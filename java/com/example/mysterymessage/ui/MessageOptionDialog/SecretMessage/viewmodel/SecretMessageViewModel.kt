package com.example.mysterymessage.ui.MessageOptionDialog.SecretMessage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.data.source.DefaultRepository
import com.example.mysterymessage.data.source.remote.ScheduleNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecretMessageViewModel@Inject constructor(
    private val scheduleNotification: ScheduleNotification,
    private val repository: DefaultRepository
    ) :ViewModel() {
    private var dataSecretMessage:MutableLiveData<DataSecretMessage> = MutableLiveData()
    val _dataSecretMessage:LiveData<DataSecretMessage>

        get() = dataSecretMessage
    private var sendMessageState = MutableLiveData<Boolean>(false)
    val _sendMessageState:LiveData<Boolean> = sendMessageState
    fun setDataSecretMessage(dataSecretMessage: DataSecretMessage){
        this.dataSecretMessage.value = dataSecretMessage

    }
    fun setDataSend(token:String,friendUserId:String,userName:String){
        dataSecretMessage.value?.token = token
        dataSecretMessage.value?.userReceiverId = friendUserId
        dataSecretMessage.value?.userNameSender = userName

    }
    fun scheduleNotification(){
        viewModelScope.launch {
            if(dataSecretMessage.value != null)
            {
                val result = repository.schedulePushNotification(dataSecretMessage.value!!)
                if(result.success){
                            sendMessageState.postValue(true)
                    }else{
                        sendMessageState.postValue(false)
                    }
                }
            }
        }
    }




