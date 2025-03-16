package com.example.mysterymessage.ui.AddFriend

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.source.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.mysterymessage.ui.login.LoginViewModel
import kotlinx.coroutines.launch

@HiltViewModel()
class FriendViewModel @Inject constructor(
    private val repository: DefaultRepository,

) :ViewModel(){
    private var listFriend:MutableLiveData<List<User>?> = MutableLiveData()
    val _listFriend :LiveData<List<User>?>  = listFriend
    private var listFriendAdd:MutableLiveData<List<User>?> = MutableLiveData()
    val _listFriendAdd :LiveData<List<User>?>  = listFriendAdd
    private var listFriendRequest:MutableLiveData<List<User>?> = MutableLiveData()
    val _listFriendRequest :LiveData<List<User>?>  = listFriendRequest
    private var hasFetchedFriend = false
    private var hasFetchedFriendRequest = false


    fun searchUser(username: String) {
        viewModelScope.launch {
            repository.findUserByUserName(username)
                .collect { result ->
                    listFriend.postValue(result)
                }
        }
    }
    fun sendFriendRequest(userName: String,friendUserName:String){
            viewModelScope.launch {
                repository.sendFriendRequest(userName,friendUserName)
            }
    }
    fun listFriendRequestByUid(uid: String,forceRefresh:Boolean) {
        if (!hasFetchedFriendRequest || forceRefresh ) {
        viewModelScope.launch {
            hasFetchedFriendRequest = true
            repository.findListFriendRequestWithUID(uid)
                .collect { result ->
                    listFriendRequest.postValue(result)
                }
        }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun listFriendByUid(uid: String,forceRefresh :Boolean) {
        if(!hasFetchedFriend || forceRefresh)
        viewModelScope.launch {
            hasFetchedFriend = true
            repository.findListFriendWithUID(uid)
                .collect { result ->
                    listFriendAdd.postValue(result)
                }
        }
    }
    fun acceptFriendRequest(username: String,friendUserName: String){
        viewModelScope.launch {
            repository.acceptFriendRequest(username,friendUserName)
            val updatedList = listFriendRequest.value?.filterNot { it.userName == friendUserName }
            listFriendRequest.postValue(updatedList)
        }
    }

}