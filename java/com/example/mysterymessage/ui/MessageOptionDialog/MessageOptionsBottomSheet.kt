package com.example.mysterymessage.ui.MessageOptionDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mysterymessage.R
import com.example.mysterymessage.databinding.FragmentOptionSendMessageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
class MessageOptionsBottomSheet(private val listener: OnOptionClickListener):BottomSheetDialogFragment() {

    private lateinit var binding:FragmentOptionSendMessageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentOptionSendMessageBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.optionSecret.setOnClickListener{
            listener.onOptionSelect("secret_message")
            dismiss()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_FullScreenBottomSheet)
    }

    interface OnOptionClickListener{
        fun onOptionSelect(option:String)
    }
}