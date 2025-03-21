package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.FragmentStepSendMessage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.databinding.FragmentStep2SecretMessageBinding
import com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.viewmodel.SecretMessageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.Locale
@AndroidEntryPoint
class Step2Fragment :Fragment() {
    private lateinit var binding : FragmentStep2SecretMessageBinding
    private var selectedDate: String? = null
    private var selectedTime: String? = null
    private val viewModel: SecretMessageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentStep2SecretMessageBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                binding.edtDate.setText(formattedDate)
                selectedDate = formattedDate
            }, year, month, day)
            datePicker.setOnShowListener{
                datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            }

            datePicker.show()
        }
        binding.edtTime.setOnClickListener {

            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(requireContext(),
                { _, selectedHour, selectedMinute ->
                   val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    binding.edtTime.setText(formattedTime)
               selectedTime = formattedTime
                }, hour, minute, true
            )

            timePickerDialog.setOnShowListener {
                timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)  // Nút OK
                timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            }
            timePickerDialog.show()
        }
        binding.btnConfirm.setOnClickListener {
            if (selectedDate != null && selectedTime != null) {
                val iso8601Time = mergeDateTimeToISO8601(selectedDate!!, selectedTime!!)
                var dataMessage= viewModel._dataSecretMessage.value?: DataSecretMessage()
                dataMessage.sendTime = iso8601Time
                viewModel.setDataSecretMessage(dataMessage)
            }
            (parentFragment as StepNavigator).goToNextStep()
        }
    }
    private fun mergeDateTimeToISO8601(date: String, time: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")

        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh") // Chuyển đổi về UTC+7

        val dateObject = inputFormat.parse("$date $time")
        return outputFormat.format(dateObject!!)
    }

}