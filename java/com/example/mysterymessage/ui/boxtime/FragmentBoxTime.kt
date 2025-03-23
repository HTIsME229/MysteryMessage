package com.example.mysterymessage.ui.boxtime

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mysterymessage.NavGraphDirections
import com.example.mysterymessage.R
import com.example.mysterymessage.databinding.FragmentBoxTimeBinding
import com.example.mysterymessage.ui.boxtime.adapter.ScheduledMessageAdapter
import com.example.mysterymessage.ui.boxtime.adapter.SkeletonAdapter
import com.example.mysterymessage.ui.boxtime.viewmodel.BoxTimeViewModel
import com.example.mysterymessage.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBoxTime : Fragment(), MenuProvider {
    private lateinit var mBinding: FragmentBoxTimeBinding
    private lateinit var navController: NavController
    private var avatar: ImageView? = null
    private lateinit var adapter: ScheduledMessageAdapter
    private val viewModel: LoginViewModel by activityViewModels()
    private val scheduledMessageViewModel: BoxTimeViewModel by activityViewModels()
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
                val action = NavGraphDirections.actionGlobalFragmentLogin2()
                navController.navigate(action)
            }
        else{
            viewModel.refreshUser(currentUser.uid)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        checkLogin()
        mBinding.floatingActionButton.setOnClickListener {
            val action = FragmentBoxTimeDirections.actionFragmentBoxTimeToFragmentAddFriend2()
            navController.navigate(action)
        }
        viewModel._profile.observe(viewLifecycleOwner) {
            if (it != null)
            {
                updateAvatar()
                mBinding.progressBar.visibility=View.GONE
                }
        }
       val skeletonAdapter = SkeletonAdapter()
        mBinding.recyclerFriendMessageSkeleton.adapter=skeletonAdapter
        mBinding.recyclerFriendMessageSkeleton.visibility = View.VISIBLE // Hiện shimmer

        mBinding.shimmerViewContainer.startShimmer() // Bắt đầu shimmer effect
        mBinding.recyclerFriendMessage.visibility = View.GONE // Ẩn danh sách thật
        adapter = ScheduledMessageAdapter(requireContext())
        mBinding.recyclerFriendMessage.adapter = adapter
        viewModel._profile.observe(viewLifecycleOwner)
        {
            if(it != null)
            {
                scheduledMessageViewModel.loadScheduledMessageData(it.userName,false)
            }
        }
        scheduledMessageViewModel._scheduledMessageListLiveData.observe(viewLifecycleOwner)
        {
            if(it != null)
            {
                adapter.updateListScheduleMessage(it)
                mBinding.recyclerFriendMessageSkeleton.visibility = View.GONE
                mBinding.shimmerViewContainer.stopShimmer()
                mBinding.shimmerViewContainer.visibility = View.GONE
                mBinding.recyclerFriendMessage.visibility = View.VISIBLE
            }
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

}