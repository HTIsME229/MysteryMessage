package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.mysterymessage.R
import com.example.mysterymessage.databinding.FragmentSecretMessageBinding
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage.EssayChoiceFragment
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage.StepNavigator
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.adapter.StepPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSecretMessage:Fragment(),StepNavigator,MenuProvider{
    private lateinit var binding: FragmentSecretMessageBinding
     var currentStep4Fragment: Fragment = EssayChoiceFragment()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecretMessageBinding.inflate(inflater, container, false)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        var adapter = StepPagerAdapter(this,this)
        binding.viewPagerSecretMessageStep.adapter = adapter
        val activity = requireActivity() as AppCompatActivity

        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        activity.supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        activity.supportActionBar?.elevation = 0f
        val customView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar_secre_message, null)
        activity.supportActionBar?.apply {
            setDisplayShowCustomEnabled(true)
            setDisplayShowTitleEnabled(false)
            setCustomView(customView) // Gán View thực tế
        }


        binding.viewPagerSecretMessageStep.isUserInputEnabled = false
        binding.viewPagerSecretMessageStep.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                requireActivity().invalidateOptionsMenu() // Buộc cập nhật lại menu khi chuyển trang
            }
        })
        return binding.root// Ngăn vuốt để chuyển bước
    }



    override fun goToNextStep() {

            val nextItem = binding.viewPagerSecretMessageStep.currentItem + 1
            if (nextItem < binding.viewPagerSecretMessageStep.adapter!!.itemCount) {
                binding.viewPagerSecretMessageStep.currentItem = nextItem
            }


          }

    override fun backStep() {
        val backItem = binding.viewPagerSecretMessageStep.currentItem - 1
        if (backItem >= 0) {
            binding.viewPagerSecretMessageStep.currentItem = backItem
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setStep4Fragment(fragment: Fragment) {
        currentStep4Fragment = fragment
        binding.viewPagerSecretMessageStep.adapter?.let {
            it.notifyItemChanged(3)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
           menuInflater.inflate(R.menu.menu_send_message,menu)

        }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        menu.findItem(R.id.back)?.isVisible =
            binding.viewPagerSecretMessageStep.currentItem != 0
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
     return   when(menuItem.itemId){
         R.id.back-> {
                backStep()
             Log.d("checkHome","home")
                true
            }

            else -> false
        }

        }
}