package com.example.mysterymessage.ui.LetterToYou.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.data.source.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LetterToYouViewModel@Inject constructor(
    private val repository: DefaultRepository,

    ):ViewModel() {
        private  var listLetterToYou : MutableLiveData<List<DataSecretMessage>?> = MutableLiveData()
        val _listLetterToYou :LiveData<List<DataSecretMessage>?> =listLetterToYou
    private var hasFetchedLetterToYou = false
    fun loadListLetterToYouData(uid:String,forceRefresh:Boolean){
        if(!hasFetchedLetterToYou || forceRefresh)
        {
            viewModelScope.launch {
                repository.getLetterToYou(uid)
                    .collect{
                        if(it !=null){
                            listLetterToYou.postValue(it)
                        }
                        else {
                            listLetterToYou.postValue(null)
                        }
                    }
            }
        }

    }

}