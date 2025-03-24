package com.example.mysterymessage.ui.LetterToYou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mysterymessage.databinding.FragmentLetterToYouBinding
import com.example.mysterymessage.ui.LetterToYou.adapter.LetterToYouAdapter
import com.example.mysterymessage.ui.LetterToYou.viewmodel.LetterToYouViewModel
import com.example.mysterymessage.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentLetterToYou :Fragment() {
    private lateinit var mbinding:FragmentLetterToYouBinding
    private val viewModel: LoginViewModel by activityViewModels()
    private val letterToYouViewModel: LetterToYouViewModel by activityViewModels()
    private lateinit var adapter: LetterToYouAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding= FragmentLetterToYouBinding.inflate(inflater,container,false)


        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LetterToYouAdapter(requireContext())
        mbinding.listLetterToYou.adapter= adapter
        viewModel._profile.observe(viewLifecycleOwner){
            if(it != null){
                letterToYouViewModel.loadListLetterToYouData(it.uid,false)
            }

        }
        letterToYouViewModel._listLetterToYou.observe(viewLifecycleOwner){
                adapter.updateListLetterToYou(it)
        }
    }
}