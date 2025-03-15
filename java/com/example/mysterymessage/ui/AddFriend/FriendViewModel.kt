package com.example.mysterymessage.ui.AddFriend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.source.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel()
class FriendViewModel @Inject constructor(
    private val repository: DefaultRepository
) :ViewModel(){
    private var listFriend:MutableLiveData<List<User>?> = MutableLiveData()
    val _listFriend :LiveData<List<User>?>  = listFriend
    private var listFriendRequest:MutableLiveData<List<User>?> = MutableLiveData()
    val _listFriendRequest :LiveData<List<User>?>  = listFriendRequest

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
    fun listFriendRequestByUid(uid: String) {
        viewModelScope.launch {
            repository.findListFriendRequestWithUID(uid)
                .collect { result ->
                    listFriendRequest.postValue(result)
                }
        }
    }

}