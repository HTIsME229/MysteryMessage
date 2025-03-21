package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.databinding.FragmentStep1SecretMessageBinding
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.viewmodel.SecretMessageViewModel
import com.example.mysterymessage.ui.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Step1Fragment:Fragment (){
    private lateinit var binding: FragmentStep1SecretMessageBinding
    private val viewModel: SecretMessageViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentStep1SecretMessageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener{
            val title = binding.edTitle.text.toString()
            val message = binding.edMessage.text.toString()
            var dataMessage = viewModel._dataSecretMessage.value?: DataSecretMessage()
            dataMessage.title = title
            dataMessage.message = message
            viewModel.setDataSecretMessage(dataMessage!!)
            if(!title.isEmpty() && !message.isEmpty()){
                (requireParentFragment() as? StepNavigator)?.goToNextStep()
            }

        }
    }
}