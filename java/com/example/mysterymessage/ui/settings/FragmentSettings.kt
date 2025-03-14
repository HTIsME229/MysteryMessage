package com.example.mysterymessage.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.databinding.FragmentSettingBinding
import com.example.mysterymessage.ui.chat.FragmentChatDirections
import com.example.mysterymessage.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSettings:Fragment() {
    private lateinit var binding:FragmentSettingBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logout.setOnClickListener{
            val sharedPref = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("LOGIN_SUCCESS", false).apply()
            viewModel.logOut()
        }
        viewModel._profile.observe(viewLifecycleOwner)
        {
            if(it != null){
                setUpView(it)
            }
        }
        viewModel._logoutState.observe(viewLifecycleOwner){
            if(it) {
                val currentDestination = findNavController().currentDestination
                Log.d("NavDebug", "Current Destination: ${currentDestination?.label} (${currentDestination?.id})")
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpView(user: User) {
        binding.userName.text = user.userName
        Glide.with(requireContext()) // hoặc requireContext() nếu trong Fragment
            .load(user.avatar) // URL ảnh
            .placeholder(R.drawable.avatar2) // Ảnh hiển thị khi đang tải
            .error(R.drawable.ic_launcher_background) // Ảnh hiển thị khi lỗi
            .circleCrop() // Cắt ảnh thành hình tròn (tuỳ chọn)
            .into(binding.profileImage)    }
}