package com.example.mysterymessage.ui.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mysterymessage.MessengerUTILS
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.source.Repository
import com.example.mysterymessage.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :Fragment() {
    private lateinit var mBinding :FragmentRegisterBinding
    private var selectedAvatar: ImageView? = null
    private var selectedTick: ImageView? = null
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       mBinding= FragmentRegisterBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAction()
    }

    private fun setUpAction() {

        val avatars = listOf(
        mBinding.includeRegister.avatar1,
            mBinding.includeRegister.avatar2,
            mBinding.includeRegister.avatar3,
            mBinding.includeRegister.avatar4,
            mBinding.includeRegister.avatar5,
            mBinding.includeRegister.avatar6,
        )

        val ticks = listOf(
            mBinding.includeRegister.tick1,
            mBinding.includeRegister.tick2,
            mBinding.includeRegister.tick3,
            mBinding.includeRegister.tick4,
            mBinding.includeRegister.tick5,
            mBinding.includeRegister.tick6,
        )

        avatars.forEachIndexed { index, avatar ->
            avatar.setOnClickListener {
                selectedAvatar= null
                selectedTick?.visibility = View.GONE
                ticks[index].visibility = View.VISIBLE
                selectedTick = ticks[index]
                selectedAvatar=avatars[index]
            }
        }
        viewModel._registerFormState.observe(viewLifecycleOwner){
            if (it != null) {
                if(!it.isCorrect){

                    mBinding.editRegisterEmailLayout.error= it.emailError?.let { it1 -> getString(it1) }
                    mBinding.editRegisterUsernameLayout.error= it.userNameError?.let { e ->getString(e) }
                    mBinding.editRegisterPasswordLayout.error= it.password?.let { e ->getString(e) }
                    mBinding.editRegisterConfirmPasswordLayout.error= it.confirmPassword?.let { e ->getString(e) }

                }
                mBinding.progressBarRegister.visibility= ProgressBar.GONE

            }
        }
        viewModel._registerState.observe(viewLifecycleOwner){
            if(it == "Success"){
                mBinding.progressBarRegister.visibility= ProgressBar.GONE
              findNavController().popBackStack()
            }
            else{
                mBinding.progressBarRegister.visibility= ProgressBar.GONE
                    Snackbar.make(mBinding.root,it,Snackbar.LENGTH_LONG).show()
            }
        }
        mBinding.buttonRegister.setOnClickListener{regisrer()}

    }
    @SuppressLint("SuspiciousIndentation")
    fun regisrer(){
        mBinding.progressBarRegister.visibility= ProgressBar.VISIBLE
        val displayName = mBinding.editRegisterDisplayName.text.toString()
        val userName = mBinding.editRegisterUsername.text.toString()
        val email = mBinding.editRegisterEmail.text.toString()
        val password = mBinding.editRegisterPassword.text.toString()
        val confirmPassword = mBinding.editRegisterConfirmPassword.text.toString()
        val imageName ="https://github.com/HTIsME229/LTC-_Chuong1/blob/bai1/${selectedAvatar?.let { it.context.resources.getResourceEntryName(it.id)}}.png" ;
        val token  =MessengerUTILS.token

        if(token != null)
        {
            if(viewModel.loginDataChanged(email,userName,password,confirmPassword,imageName))
            viewModel.register(User(userName,email,displayName,password,imageName,ArrayList(),token))
        }

    }



}