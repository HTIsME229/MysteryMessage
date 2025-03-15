package com.example.mysterymessage.data.source

import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.source.remote.ResponseResult
import com.example.mysterymessage.ui.login.LoginData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body

interface DataSource {
    interface RemoteDataSource {
        suspend fun findUserByUserName(username:String):Flow<List<User>?>

        suspend fun  createAccount(user: User):String
        suspend fun  updateAccount(user: User):Boolean

        suspend fun sendFriendRequest (userName:String,friendUserName:String):ResponseResult
         fun  findUserWithUID( uid: String): Flow<User?>
    }
    interface  LocalDataSource{

    }
}