package com.example.mysterymessage.ui.boxtime

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mysterymessage.NavGraphDirections
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.databinding.FragmentBoxTimeBinding
import com.example.mysterymessage.ui.boxtime.viewmodel.BoxTimeViewModel
import com.example.mysterymessage.ui.login.LoginActivity
import com.example.mysterymessage.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class FragmentBoxTime : Fragment(), MenuProvider {
    private lateinit var mBinding: FragmentBoxTimeBinding
    private lateinit var navController: NavController
    private var avatar: ImageView? = null
    private val viewModel: LoginViewModel by activityViewModels()
    private val boxTimeViewModel: BoxTimeViewModel by activityViewModels()
    private  var  profile :User?= null
    private var selectedMonth = 0
    private var selectedYear = 0
    private val daysInMonth = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBoxTimeBinding.inflate(inflater, container, false)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return mBinding.root
    }
    @SuppressLint("SuspiciousIndentation")
    private fun checkLogin() {
        mBinding.progressBar.visibility=View.VISIBLE
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(viewModel._profile.value == null)
            if(currentUser == null){
                val currentDestination = navController.currentDestination
                Log.d(
                    "NavDebug",
                    "Current Destination: ${currentDestination?.label} (${currentDestination?.id})"
                )
                val loginIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(loginIntent)
            }
        else{
            viewModel.refreshUser(currentUser.uid)
        }


    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        checkLogin()
        viewModel._profile.observe(viewLifecycleOwner) {
            if (it != null)
            {
                profile = it
                updateAvatar()
                mBinding.progressBar.visibility=View.GONE
                }
        }

        mBinding.calender.txtMonth.setOnClickListener{
            openMonthYearPickerDialog()
        }
        val calendar = Calendar.getInstance()
        selectedMonth = calendar[Calendar.MONTH] +1
        selectedYear = calendar[Calendar.YEAR]

        updateCalendar()
        setUpViewModel()
        setUpAction()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setUpViewModel(){
        viewModel._profile.observe(viewLifecycleOwner)
        {
            if(it != null)
            {
                boxTimeViewModel.loadMessageData(it.userName,false)
            }
        }
        boxTimeViewModel._MessageListLiveData.observe(viewLifecycleOwner){
            if(it != null)
            {
                updateCalendar()
            }
        }
    }
    fun setUpAction(){
        mBinding.floatingActionButton.setOnClickListener {
            val action = FragmentBoxTimeDirections.actionFragmentBoxTimeToFragmentAddFriend2()
            navController.navigate(action)
        }

    }


    private fun updateAvatar() {
        val user = viewModel._profile.value
        if (user != null) {
            avatar?.let { it1 ->
                Glide.with(requireContext()) // hoặc requireContext() nếu trong Fragment
                    .load(user.avatar) // URL ảnh
                    .placeholder(R.drawable.avatar2) // Ảnh hiển thị khi đang tải
                    .error(R.drawable.ic_launcher_background) // Ảnh hiển thị khi lỗi
                    .circleCrop() // Cắt ảnh thành hình tròn (tuỳ chọn)
                    .into(it1)
            };
        } else {
            avatar?.let { it1 ->
                Glide.with(requireContext()) // hoặc requireContext() nếu trong Fragment
                    .load(R.drawable.logout) // URL ảnh
                    .error(R.drawable.ic_launcher_background) //
                    .placeholder(R.drawable.logout) // Ảnh hiển thị khi đang tải
                    .circleCrop() // Cắt ảnh thành hình tròn (tuỳ chọn)
                    .into(it1)
            };
        }

    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_chat_fragment, menu)
        val actionView = menu.findItem(R.id.menu_avatar).actionView
        actionView?.let {
            avatar = it.findViewById(R.id.menu_item_avatar) // Gán avatar đúng cách
        }
        avatar?.setOnClickListener {
            val currentDestination = navController.currentDestination
            Log.d(
                "NavDebug",
                "Current Destination: ${currentDestination?.label} (${currentDestination?.id})"
            )
            val action = FragmentBoxTimeDirections.actionFragmentBoxTimeToFragmentSettings3()
            navController.navigate(action)
        }
        updateAvatar()


    }
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openMonthYearPickerDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_month_year_picker, null)
        val monthPicker = dialogView.findViewById<NumberPicker>(R.id.monthPicker)
        val yearPicker = dialogView.findViewById<NumberPicker>(R.id.yearPicker)

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = selectedMonth // Mặc định tháng đã chọn

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        yearPicker.minValue = 2000 // Năm nhỏ nhất
        yearPicker.maxValue = currentYear + 10 // Năm lớn nhất (tương lai)
        yearPicker.value = selectedYear // Mặc định năm đã chọn

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Chọn tháng & năm")
            .setView(dialogView)
            .show()
        dialogView.findViewById<Button>(R.id.btnConfirm).setOnClickListener{
            selectedMonth = monthPicker.value
            selectedYear = yearPicker.value
            updateCalendar()
            dialog.dismiss()


        }
        dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener{
            dialog.dismiss()
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun updateCalendar() {

                mBinding.calender.txtMonth.setText(("Tháng $selectedMonth").toString() + " " + selectedYear)
        val days = if (( isLeapYear(selectedYear))) 29 else daysInMonth[selectedMonth-1]
        Log.d("Calendar", "Days in month: $days")

        mBinding.calender.gridCalendar.removeAllViews()
        mBinding.calender.gridCalendar.columnCount = 7
        val messageList = boxTimeViewModel._MessageListLiveData.value?.filter {
            getMonthFromISO(it.sendTime) == selectedMonth && getYearFromISO(it.sendTime) == selectedYear }
        for (i in 0 until days) {
            val hasMessage = messageList?.filter { message -> getDayFromISO(message.sendTime) == i + 1 } ?: emptyList()
            Log.d("checkMessage","${i+1} +${hasMessage.toString()}")
            val dotView: View = if (hasMessage.isNotEmpty())
            {
                when(hasMessage[0].status)
                {
                    "sent" -> layoutInflater.inflate(R.layout.item_sent, mBinding.calender.gridCalendar, false)
                    "canceled" -> layoutInflater.inflate(R.layout.item_canceled, mBinding.calender.gridCalendar, false)
                    "scheduled" -> layoutInflater.inflate(R.layout.item_scheduled, mBinding.calender.gridCalendar, false)
                    else -> layoutInflater.inflate(R.layout.item_dot, mBinding.calender.gridCalendar, false)
                }

            }

            else {
                layoutInflater.inflate(R.layout.item_dot, mBinding.calender.gridCalendar, false)
            }
            if (hasMessage.isNotEmpty()) {
                dotView.setOnClickListener {
                    FragmentDialogListMessage(object:FragmentDialogListMessage.onLetterChoicedListener{
                        override fun onLetterClick(dataSecretMessage: DataSecretMessage) {
                            val action = NavGraphDirections.actionGlobalFragmentDetailSecretMessage3(
                                title= dataSecretMessage.title,
                                message = dataSecretMessage.message,
                                userReceiverId = dataSecretMessage.userReceiverId,
                                userNameSender = dataSecretMessage.userNameSender
                            )
                            navController.navigate(action)
                        }

                    },hasMessage).show(
                        childFragmentManager, FragmentDialogListMessage.TAG)                }
            }

            val params = GridLayout.LayoutParams()
            params.rowSpec = GridLayout.spec(i / 7, 1f)
            params.columnSpec = GridLayout.spec(i % 7, 1f)
            params.setMargins(8, 8, 8, 8)

            dotView.layoutParams = params
            mBinding.calender.gridCalendar.addView(dotView)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthFromISO(date: String): Int {
        val localDate = LocalDate.parse(date.substring(0, 10), DateTimeFormatter.ISO_DATE)
        return localDate.monthValue
    }
    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getYearFromISO(date: String): Int {
        val localDate = LocalDate.parse(date.substring(0, 10), DateTimeFormatter.ISO_DATE)
        return localDate.year
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayFromISO(date: String): Int {
        val localDate = LocalDate.parse(date.substring(0, 10), DateTimeFormatter.ISO_DATE)
        return localDate.dayOfMonth
    }

}