package com.example.mysterymessage.ui.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.source.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel@Inject constructor(
    private val repository: DefaultRepository,
    private val application: Application
) :ViewModel() {
    private var profile :MutableLiveData<User?>  =MutableLiveData()
    val _profile :LiveData<User?> = profile
    private var loginFormState: MutableLiveData<LoginFormState?> = MutableLiveData();
    val _loginFormsState: LiveData<LoginFormState?> = loginFormState
    private  var logoutState:MutableLiveData<Boolean> = MutableLiveData()
    val _logoutState:LiveData<Boolean> = logoutState

    init {

    }
        fun login(userName:String ,password: String,token:String) {
            val user  =LoginData(userName, password, token)

            viewModelScope.launch {
                repository.login(user).collectLatest { result ->
                    // Xử lý kết quả đăng nhập ở đây
                    if (result != null) {
                        profile.postValue(result)
                        Log.d("profile",result.toString())
                        logoutState.postValue(false)

                    } else {
                    profile.postValue(null)
                        logoutState.postValue(false)
                    }
                }

        }


    }
    fun logOut(){

        profile.postValue(null)
        logoutState.postValue(true)

    }

    fun   loginDataChanged(email: String,password: String):Boolean{
        var isValid = true
        var tmploginFormState = LoginFormState()
        if(email.isBlank())
        {
            tmploginFormState.userNameError=R.string.user_name_blank
            isValid =false

        }

        if(!isPasswordValid(password)){
            tmploginFormState.passwordError= R.string.password_blank
            isValid =false

        }
        tmploginFormState.isCorrect = isValid
        loginFormState.postValue(tmploginFormState)
        return  isValid


    }
    private  fun isPasswordValid(password: String):Boolean{
        return password.isNotBlank();
    }

}