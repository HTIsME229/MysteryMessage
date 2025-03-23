package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FramgetAnswerQuestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mysterymessage.R
import com.example.mysterymessage.databinding.FragmentDetailSecretMessageBinding

class FragmentDetailSecretMessage:Fragment() {
    private  lateinit var binding:FragmentDetailSecretMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDetailSecretMessageBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val title = args?.getString("title")
        val message = args?.getString("message")
        val userNameSender = args?.getString("userNameSender")
        setUpView(title,message,userNameSender)
        binding.btnClose.setOnClickListener{
            findNavController().popBackStack(R.id.fragmentBoxTime, true)
        }
    }

    private fun setUpView(title: String?, message: String?, userNameSender: String?) {
        binding.tvTitle.text = title
        binding.tvMessageContent.text = message
        binding.tvSender.text = userNameSender
    }

}