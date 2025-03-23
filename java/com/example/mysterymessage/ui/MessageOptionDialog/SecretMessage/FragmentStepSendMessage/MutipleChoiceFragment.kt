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
import com.example.mysterymessage.data.model.dto.MultipleChoice
import com.example.mysterymessage.databinding.FragmentMultipleSecretMessageBinding
import com.example.mysterymessage.ui.AddFriend.FriendViewModel
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.viewmodel.SecretMessageViewModel
import com.example.mysterymessage.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MutipleChoiceFragment:Fragment() {
    private lateinit var binding:FragmentMultipleSecretMessageBinding
    private val viewModel: SecretMessageViewModel by activityViewModels()
    private val friendViewModel: FriendViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMultipleSecretMessageBinding.inflate(inflater,container,false)

    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener{

            showDialog {
                if(it){
                    val question = binding.etQuizQuestion.text.toString()
                    val choice1 = binding.etOption1.text.toString()
                    val choice2=  binding.etOption2.text.toString()
                    val choice3 = binding.etOption3.text.toString()
                    val choice4 = binding.etOption4.text.toString()
                    val choiceGroup = binding.rgOptions.checkedRadioButtonId
                    val correctAnswer = when(choiceGroup) {
                        binding.rbOption1.id -> choice1
                        binding.rbOption2.id -> choice2
                        binding.rbOption3.id -> choice3
                        binding.rbOption4.id -> choice4
                        else -> ""
                    }
                    if(question.isNotEmpty() && correctAnswer.isNotEmpty()){
                        val dataMessage = viewModel._dataSecretMessage.value?: DataSecretMessage()
                        dataMessage.dataQuestion = DataQuestion(question,correctAnswer, MultipleChoice(choice1,choice2,choice3,choice4))
                        viewModel.setDataSecretMessage(dataMessage)
                        viewModel.setDataSend(friendViewModel.getSelectedFriend()?.token!!,
                            friendViewModel.getSelectedFriend()?.uid!!,
                            loginViewModel.getProfile()?.userName!!,
                        )
                        viewModel.scheduleNotification()
                    }
                }

            }



        }
    }

    private fun showDialog(onResult: (Boolean) -> Unit) {
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
}