package com.example.mysterymessage.data.source

import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.source.remote.ResponseResult
import com.example.mysterymessage.ui.login.LoginData
import kotlinx.coroutines.flow.Flow

interface Repository {
    interface RemoteRepository:Repository{
        fun login(user: LoginData):Flow<User?>
        suspend fun createAccount(user: User):String
        suspend fun updateAccount(user: User):Boolean
        suspend fun findUserByUserName(username:String):Flow<List<User>?>
       suspend fun sendFriendRequest (userName:String,friendUserName:String):ResponseResult
    }
    interface LocalRepository:Repository{}
}