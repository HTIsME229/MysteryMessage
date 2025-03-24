package com.example.mysterymessage.ui.LetterToYou.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.databinding.BoxTimeItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class LetterToYouAdapter(
    private val context: Context,
) : RecyclerView.Adapter<LetterToYouAdapter.ViewHolder>() {
    private lateinit var binding: BoxTimeItemBinding;
    var listLetterToYou: List<DataSecretMessage>? = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateListLetterToYou(listLetterToYou: List<DataSecretMessage>?) {
        this.listLetterToYou = listLetterToYou
        notifyDataSetChanged()

    }


    inner class ViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("NotifyDataSetChanged")
        fun bindData(dataSecretMessage: DataSecretMessage) {
            binding.tvMessageTitle.text = context.getString(R.string.message_title,dataSecretMessage.title)
            val sendTime  = formatDate(dataSecretMessage.sendTime)
            binding.tvSendTime.text=context.getString(R.string.message_sendTime,sendTime)
            binding.tvFriendReceiver.text = context.getString(R.string.message_sendTo,dataSecretMessage.userNameReceiver)
            binding.tvMessageContent.text=dataSecretMessage.message
        }
        fun formatDate(inputDate: String): String {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterToYouAdapter.ViewHolder {
        binding = BoxTimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder( binding.root)
    }

    override fun getItemCount(): Int {
        return listLetterToYou?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listLetterToYou?.get(position)?.let { holder.bindData(it) }
    }

}