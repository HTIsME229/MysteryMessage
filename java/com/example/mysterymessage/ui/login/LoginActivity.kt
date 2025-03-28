package com.example.mysterymessage.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.example.mysterymessage.MessengerUTILS
import com.example.mysterymessage.databinding.FragmentLoginBinding
import com.example.mysterymessage.ui.register.ActivityRegister
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity:AppCompatActivity() {
    private  lateinit var mBinding:FragmentLoginBinding
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var navController: NavController

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
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
        mBinding
            .btnRegister.setOnClickListener {
                val loginIntent = Intent(this, ActivityRegister::class.java)
                startActivity(loginIntent)
            }
    }

    private fun setUpObserver() {
        viewModel._loginFormsState.observe(this){
            if(it != null){
                if(!it.isCorrect){
                    mBinding.emailInputLayout.error = it.userNameError?.let { it1 -> getString(it1) }
                    mBinding.passwordInputLayout.error=it.passwordError?.let { it1 -> getString(it1) }
                }
            }
        }
        viewModel._profile.observe(this){
            if(it != null){
                val currentDestination = navController.currentDestination
                onBackPressedDispatcher.onBackPressed()
                Log.d("NavDebug", "Current Destination: ${currentDestination?.label} (${currentDestination?.id})")
            }
        }
    }



}