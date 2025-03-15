package com.example.mysterymessage.ui.register

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysterymessage.R
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.source.DefaultRepository
import com.example.mysterymessage.data.source.Repository
import com.example.mysterymessage.data.source.remote.AuthenticationRepository
import com.example.mysterymessage.data.source.remote.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class RegisterViewModel @Inject constructor(
    private val repository: DefaultRepository,
    private val authenticationRepository: AuthenticationRepository,

    ) :ViewModel() {
    private var user: MutableLiveData<User?> = MutableLiveData<User?>(null);
    val _user: LiveData<User?> = user
    private var  registerState:MutableLiveData<ResponseResult> = MutableLiveData()
    val _registerState:LiveData<ResponseResult> =registerState

    private var registerFormState: MutableLiveData<RegisterFormState?> = MutableLiveData();
    val _registerFormState: LiveData<RegisterFormState?> = registerFormState
    fun loginDataChanged(
        email: String, userName: String, password: String,
        confirmPassword: String, image: String?
    ): Boolean {
        var isValid = true

        var formState = RegisterFormState() // Tạo một đối tượng RegisterFormState mới

        if (email.isBlank()) {
            formState.emailError = R.string.email_blank
            isValid = false
        } else if (!isEmailValid(email)) {
            formState.emailError = R.string.email_not_valid
            isValid = false
        }

        if (password.isBlank()) {
            formState.password = R.string.password_blank
            isValid = false
        } else if (confirmPassword != password) {
            formState.confirmPassword = R.string.password_not_match
            isValid = false
        }

        if (userName.isBlank()) {
            formState.userNameError = R.string.user_name_blank
            isValid = false
        }

        if (image == null) {
            formState.imageError = R.string.image_null
            isValid = false
        }

        formState.isCorrect = isValid
        registerFormState.value = formState // Chỉ cập nhật LiveData MỘT LẦN

        return isValid
    }

    @SuppressLint("SuspiciousIndentation")
    fun  register(user:User){
//        viewModelScope.launch {
//            try {
//                val mrepository = repository as Repository.RemoteRepository
//
//                Log.d("Register", "🔹 Bắt đầu gọi API createAccount...")
//                val result = mrepository.createAccount(user)
//                Log.d("Register", "✅ Kết quả đăng ký: $result") // Nếu không thấy log này, API có vấn đề
//                registerState.postValue(result)
//
//            } catch (e: Exception) {
//                Log.e("Register", "❌ Lỗi trong quá trình đăng ký: ${e.message}")
//                registerState.postValue("Error: ${e.message}")
//            }
//
//        }
        authenticationRepository.registerUser(user) { success, message ->
            if (success) {
                val res = ResponseResult(success = success, error = null);
                   registerState.postValue(res)
            } else {
                val res = ResponseResult(success = success, error = message);
                registerState.postValue(res)
            }
        }
    }
    private fun isEmailValid(email: String):Boolean{
        if(email.contains("@")){
            PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
            return true
        }
        else
            return false
    }
}