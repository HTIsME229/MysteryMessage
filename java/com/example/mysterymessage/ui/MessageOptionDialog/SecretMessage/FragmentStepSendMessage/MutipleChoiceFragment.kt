package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mysterymessage.databinding.FragmentMultipleSecretMessageBinding

class MutipleChoiceFragment:Fragment() {
    private lateinit var binding:FragmentMultipleSecretMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMultipleSecretMessageBinding.inflate(inflater,container,false)

    return binding.root
    }
}