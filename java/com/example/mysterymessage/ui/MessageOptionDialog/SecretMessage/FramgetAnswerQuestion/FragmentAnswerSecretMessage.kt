package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FramgetAnswerQuestion

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.mysterymessage.data.model.dto.DataQuestion
import com.example.mysterymessage.databinding.FragmentAnswerMultipleSecretMessageBinding
import com.example.mysterymessage.databinding.FragmentAnswerSecretMessageBinding

class FragmentAnswerSecretMessage : Fragment() {
    private var _binding: ViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args = arguments
        val dataQuestion = args?.getParcelable<DataQuestion>("dataQuestion")

        _binding = if (dataQuestion?.multipleChoice != null) {
            FragmentAnswerMultipleSecretMessageBinding.inflate(inflater, container, false)
        } else {
            FragmentAnswerSecretMessageBinding.inflate(inflater, container, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val dataQuestion = args?.getParcelable<DataQuestion>("dataQuestion")
        val userReceiverId = args?.getString("userReceiverId")
        val userNameSender = args?.getString("userNameSender")
        val title = args?.getString("title")
        val message = args?.getString("message")

        setUpView(dataQuestion)

        // Xử lý sự kiện cho bài toán tự luận
        (binding as? FragmentAnswerSecretMessageBinding)?.apply {
            btnSubmit.setOnClickListener {
                val answer = etAnswer.text.toString()
                if (answer == dataQuestion?.answer) {
                    navigateToDetail(userNameSender, title, message)
                } else {
                    etAnswer.error = "Your answer is incorrect"
                }
            }
        }

        // Xử lý sự kiện cho bài toán trắc nghiệm
        (binding as? FragmentAnswerMultipleSecretMessageBinding)?.apply {
            btnSubmit.setOnClickListener {
                val selectedId = rgOptions.checkedRadioButtonId

                if (selectedId == -1) {
                    Toast.makeText(requireContext(), "Please select an answer", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val correctAnswer = when (selectedId) {
                    rbOption1.id -> dataQuestion?.multipleChoice?.choice1
                    rbOption2.id -> dataQuestion?.multipleChoice?.choice2
                    rbOption3.id -> dataQuestion?.multipleChoice?.choice3
                    rbOption4.id -> dataQuestion?.multipleChoice?.choice4
                    else -> null
                }

                if (correctAnswer == dataQuestion?.answer) {
                    navigateToDetail(userNameSender, title, message)
                } else {
                    tvQuizQuestion.error = "Your answer is incorrect"
                    Toast.makeText(requireContext(), "Your answer is incorrect", Toast.LENGTH_SHORT).apply {
                        setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                        show()
                    }
                }
            }
        }
    }

    private fun setUpView(dataQuestion: DataQuestion?) {
        (binding as? FragmentAnswerSecretMessageBinding)?.tvQuestion?.text = dataQuestion?.question

        (binding as? FragmentAnswerMultipleSecretMessageBinding)?.apply {
            tvQuizQuestion.text= dataQuestion?.question
            tvOption1.text = dataQuestion?.multipleChoice?.choice1
            tvOption2.text = dataQuestion?.multipleChoice?.choice2
            tvOption3.text = dataQuestion?.multipleChoice?.choice3
            tvOption4.text = dataQuestion?.multipleChoice?.choice4
        }
    }

    private fun navigateToDetail(userNameSender: String?, title: String?, message: String?) {
        val action = FragmentAnswerSecretMessageDirections
            .actionFragmentAnswerSecretMessageToFragmentDetailSecretMessage(
                userNameSender = userNameSender ?: "",
                title = title ?: "",
                message = message ?: ""
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
