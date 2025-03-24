package com.example.mysterymessage.ui.boxtime.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.data.source.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxTimeViewModel @Inject constructor(
    private val repository: DefaultRepository,

    ):ViewModel() {
     private var scheduledMessageList :MutableLiveData<List<DataSecretMessage>?> = MutableLiveData()
    val _scheduledMessageListLiveData: MutableLiveData<List<DataSecretMessage>?>
        get() = scheduledMessageList
    private var sentMessageList :MutableLiveData<List<DataSecretMessage>?> = MutableLiveData()
    val _sentMessageListLiveData: MutableLiveData<List<DataSecretMessage>?>
        get() = sentMessageList
    private var canceledMessageList :MutableLiveData<List<DataSecretMessage>?> = MutableLiveData()
    val _canceledMessageListLiveData: MutableLiveData<List<DataSecretMessage>?>
        get() = canceledMessageList
    private var hasFetchedScheduledMessage = false
    private var hasFetchedSentMessage = false
    private var hasFetchedCanceledMessage = false

    fun loadScheduledMessageData(userName:String,forceRefresh :Boolean){
        if(!hasFetchedScheduledMessage || forceRefresh){
            viewModelScope.launch {
                try {
                    repository.getScheduleMessage(userName)
                        .collect { result ->
                            if (result != null) {
                                hasFetchedScheduledMessage = true
                                scheduledMessageList.postValue(result) }
                            else
                            scheduledMessageList.postValue(null)
                        }
                } catch (_: Exception) {

                }
            }
        }

    }
    fun loadSentMessageData(userName:String,forceRefresh :Boolean){
        if(!hasFetchedSentMessage || forceRefresh){
            viewModelScope.launch {
                try {
                    repository.getSentMessage(userName)
                        .collect { result ->
                            if (result != null) {
                                hasFetchedSentMessage = true
                                sentMessageList.postValue(result) }
                            else
                            sentMessageList.postValue(null)
                        }
                } catch (_: Exception) {

                }
            }
        }

    }
    fun loadCanceledMessageData(userName:String,forceRefresh :Boolean){
        if(!hasFetchedCanceledMessage || forceRefresh){
            viewModelScope.launch {
                try {
                    repository.getCanceledMessage(userName)
                        .collect { result ->
                            if (result != null) {
                                hasFetchedCanceledMessage = true
                                canceledMessageList.postValue(result) }
                            else
                                canceledMessageList.postValue(null)
                        }
                } catch (_: Exception) {

                }
            }
        }

    }

}