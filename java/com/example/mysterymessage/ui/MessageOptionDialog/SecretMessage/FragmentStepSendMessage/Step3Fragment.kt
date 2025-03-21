package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mysterymessage.databinding.FragmentStep3SecretMessageBinding
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentSecretMessage
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.viewmodel.SecretMessageViewModel
import dagger.hilt.android.AndroidEntryPoint

class Step3Fragment:Fragment() {
    private lateinit var binding :FragmentStep3SecretMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentStep3SecretMessageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.multipleChoice.setOnClickListener{
            (parentFragment as? FragmentSecretMessage)?.setStep4Fragment(MutipleChoiceFragment())
            (parentFragment as StepNavigator).goToNextStep()

        }
        binding.essayChoice.setOnClickListener{
            (parentFragment as? FragmentSecretMessage)?.setStep4Fragment(EssayChoiceFragment())
            (parentFragment as StepNavigator).goToNextStep()
        }
    }
}