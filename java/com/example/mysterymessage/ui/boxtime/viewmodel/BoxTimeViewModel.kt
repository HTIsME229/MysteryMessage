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
     private var MessageList :MutableLiveData<List<DataSecretMessage>?> = MutableLiveData()
    val _MessageListLiveData: MutableLiveData<List<DataSecretMessage>?>
        get() = MessageList
    private var hasFetchedMessage = false

    fun loadMessageData(userName:String,forceRefresh :Boolean){
        if(!hasFetchedMessage || forceRefresh){
            viewModelScope.launch {
                try {
                    repository.getMessage(userName)
                        .collect { result ->
                            if (result != null) {
                                hasFetchedMessage = true
                                MessageList.postValue(result) }
                            else
                                MessageList.postValue(null)
                        }
                } catch (_: Exception) {

                }
            }
        }

    }


}