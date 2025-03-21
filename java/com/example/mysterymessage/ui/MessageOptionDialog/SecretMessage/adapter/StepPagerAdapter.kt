package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentSecretMessage
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage.EssayChoiceFragment
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage.Step1Fragment
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage.Step2Fragment
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage.Step3Fragment

class StepPagerAdapter(fragment: Fragment, private val parent: FragmentSecretMessage) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Step1Fragment()
            1 -> Step2Fragment()
            2-> Step3Fragment()
            3 -> parent.currentStep4Fragment
            else -> EssayChoiceFragment()
        }

    }
}