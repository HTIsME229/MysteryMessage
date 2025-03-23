package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.dto.DataQuestion
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.databinding.FragmentEssaySecretMessageBinding
import com.example.mysterymessage.ui.AddFriend.FriendViewModel
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.viewmodel.SecretMessageViewModel
import com.example.mysterymessage.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EssayChoiceFragment:Fragment() {
    private lateinit var binding: FragmentEssaySecretMessageBinding
    private val viewModel: SecretMessageViewModel by activityViewModels()
    private val friendViewModel: FriendViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            showDialog{
                if(it){
                    val question = binding.edQuestion.text.toString()
                    val answer = binding.edAnswer.text.toString()
                    if (!question.isEmpty() && !answer.isEmpty()) {
                        var dataMessage = viewModel._dataSecretMessage.value ?: DataSecretMessage()
                        dataMessage.dataQuestion = DataQuestion(question, answer)
                        viewModel.setDataSecretMessage(dataMessage)
                        viewModel.setDataSend(
                            friendViewModel.getSelectedFriend()?.token!!,
                            friendViewModel.getSelectedFriend()?.uid!!,
                            loginViewModel.getProfile()?.userName!!,
                        )
                        viewModel.scheduleNotification()
                    }
                }

            }


        }

    }

    private fun showDialog(onResult:(Boolean)->Unit ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder
            .setMessage(getString(R.string.message_send_message))
            .setTitle(getString(R.string.title_send_message))
            .setPositiveButton(getString(R.string.button_send)) { dialog, which ->
                onResult(true)
            }
            .setNegativeButton(getString(R.string.button_cancel)) { dialog, which ->
                onResult(false)
                }

        val dialog: AlertDialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        }
        dialog.show()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentEssaySecretMessageBinding.inflate(inflater,container,false)
        return binding.root
    }
}