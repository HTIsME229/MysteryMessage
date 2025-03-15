package com.example.mysterymessage.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mysterymessage.MessengerUTILS
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.databinding.FragmentLoginBinding
import com.example.mysterymessage.ui.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentLogin:Fragment() {
    private  lateinit var mBinding:FragmentLoginBinding
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var navController: NavController

    private val viewModel: LoginViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController();
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= FragmentLoginBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btnRegister.setOnClickListener{
            val action = FragmentLoginDirections.actionFragmentLogin2ToRegisterFragment()
            findNavController().navigate(action)
        }
        setAction()
        setUpObserver()

    }

    private fun setAction() {
        mBinding.loginButton.setOnClickListener {
            val username = mBinding.emailEditText.text.toString()
            val password = mBinding.passwordEditText.text.toString()
            val token = MessengerUTILS.token
            if(token != null){
                if(viewModel.loginDataChanged(username,password)){
                    viewModel.login(username,password,token)
                }
            }

        }
    }

    private fun setUpObserver() {
        viewModel._loginFormsState.observe(viewLifecycleOwner){
            if(it != null){
                if(!it.isCorrect){
                    mBinding.emailInputLayout.error = it.userNameError?.let { it1 -> getString(it1) }
                    mBinding.passwordInputLayout.error=it.passwordError?.let { it1 -> getString(it1) }
                }
            }
        }
        viewModel._profile.observe(viewLifecycleOwner){
            if(it != null){
                val currentDestination = navController.currentDestination
                Log.d("NavDebug", "Current Destination: ${currentDestination?.label} (${currentDestination?.id})")
                navController.popBackStack()
            }
        }
    }


}