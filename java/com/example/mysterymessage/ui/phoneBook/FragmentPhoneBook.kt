package com.example.mysterymessage.ui.phoneBook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mysterymessage.databinding.FragmentPhoneBookBinding

class FragmentPhoneBook :Fragment() {
    private lateinit var mbinding:FragmentPhoneBookBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding= FragmentPhoneBookBinding.inflate(inflater,container,false)
        return mbinding.root
    }
}