package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FramgetAnswerQuestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mysterymessage.data.model.dto.DataQuestion
import com.example.mysterymessage.databinding.FragmentAnswerSecretMessageBinding

class FragmentAnswerSecretMessage:Fragment() {
    private  lateinit var binding:FragmentAnswerSecretMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAnswerSecretMessageBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val dataQuestion = args?.getParcelable<DataQuestion>("dataQuestion")
        val userReceiverId = args?.getString("userReceiverId")
        val userNameSender = args?.getString("userNameSender")
        val title = args?.getString("title");
        val message = args?.getString("message");
        setUpView(dataQuestion,userReceiverId,userNameSender)
        binding.btnSubmit.setOnClickListener{
            val answer = binding.etAnswer.text.toString()
            if(answer.equals(dataQuestion?.answer)){
                val action = FragmentAnswerSecretMessageDirections
                    .actionFragmentAnswerSecretMessageToFragmentDetailSecretMessage(
                        userNameSender = userNameSender ?: "",
                        title = title ?: "",
                        message = message ?: ""
                    )

                findNavController().navigate(action)
            }
            else{
                binding.etAnswer.error = "Your answer is incorrect"
            }
        }

    }

    private fun setUpView(dataQuestion: DataQuestion?, userReceiverId: String?, userNameSender: String?) {
                binding.tvQuestion.text = dataQuestion?.question

    }

}