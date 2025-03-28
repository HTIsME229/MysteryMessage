package com.example.mysterymessage.ui.boxtime.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.databinding.BoxTimeItemBinding
import com.example.mysterymessage.ui.boxtime.FragmentDialogListMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ScheduledMessageAdapter(private  val listener: FragmentDialogListMessage.onLetterChoicedListener,
    private val context: Context,
) : RecyclerView.Adapter<ScheduledMessageAdapter.ViewHolder>() {

    var listScheduleMessage: List<DataSecretMessage>? = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateListScheduleMessage(listScheduleMessage: List<DataSecretMessage>?) {
        Log.d("ScheduledMessageAdapter", "Cập nhật danh sách, size: ${listScheduleMessage?.size ?: 0}")

        this.listScheduleMessage = listScheduleMessage
        notifyDataSetChanged()
    }


    inner class ViewHolder( private  val binding: BoxTimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bindData(dataSecretMessage: DataSecretMessage) {
       binding.tvMessageTitle.text = context.getString(R.string.message_title,dataSecretMessage.title)
            val sendTime  = formatDate(dataSecretMessage.sendTime)
         binding.tvSendTime.text=context.getString(R.string.message_sendTime,sendTime)
            binding.tvFriendReceiver.text = context.getString(R.string.message_sendTo,dataSecretMessage.userNameReceiver)
            binding.tvMessageContent.text=dataSecretMessage.message
            binding.root.setOnClickListener {
                listener.onLetterClick(dataSecretMessage)}


            }
        private fun formatDate(inputDate: String): String {
            return try {
                // Định dạng của chuỗi ngày giờ đầu vào
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Xử lý múi giờ nếu cần

                // Định dạng mong muốn đầu ra
                val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.getDefault())

                // Chuyển đổi
                val date: Date = inputFormat.parse(inputDate)!!
                outputFormat.format(date)
            } catch (e: Exception) {
                "Lỗi định dạng"
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledMessageAdapter.ViewHolder {
        val binding = BoxTimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder( binding)
    }

    override fun getItemCount(): Int {
        return listScheduleMessage?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        listScheduleMessage?.get(position)?.let { holder.bindData(it) }
    }

}
