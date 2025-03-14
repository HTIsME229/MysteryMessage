package com.example.mysterymessage.voicecall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mysterymessage.databinding.FragmentHomeBinding
import com.example.mysterymessage.databinding.FragmentVoiceCallBinding

class FragmentVoiceCall:Fragment() {
    private lateinit var mBinding:FragmentVoiceCallBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding=FragmentVoiceCallBinding.inflate(inflater,container,false)
        return mBinding.root
    }
}