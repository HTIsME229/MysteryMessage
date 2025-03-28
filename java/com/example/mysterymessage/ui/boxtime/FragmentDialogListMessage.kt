package com.example.mysterymessage.ui.boxtime

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.databinding.FragmentDialogListMessageBinding
import com.example.mysterymessage.ui.boxtime.adapter.ScheduledMessageAdapter

class FragmentDialogListMessage(private  val listener: onLetterChoicedListener,private  val listMessage: List<DataSecretMessage>):DialogFragment() {
    private  lateinit var  biding: FragmentDialogListMessageBinding
    private lateinit var adapter: ScheduledMessageAdapter
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        biding= FragmentDialogListMessageBinding.inflate(layoutInflater)
          val builder = AlertDialog.Builder(requireContext())
        builder.setView(biding.root)
        adapter = ScheduledMessageAdapter(listener,requireContext())
        biding.listMessage.layoutManager = LinearLayoutManager(requireContext())

        biding.listMessage.adapter = adapter
        adapter.updateListScheduleMessage(listMessage)
        return builder.create()
     }

    override fun onPause() {
        super.onPause()
        dismiss()
    }



    companion object {
        const val TAG="FragmentDialogListMessage"
    }
    interface onLetterChoicedListener{
        fun onLetterClick(dataSecretMessage: DataSecretMessage)
    }
}