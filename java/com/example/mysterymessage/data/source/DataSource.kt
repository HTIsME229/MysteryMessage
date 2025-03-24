package com.example.mysterymessage.data.source

import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.data.source.remote.ResponseResult
import kotlinx.coroutines.flow.Flow

interface DataSource {
    interface RemoteDataSource {
        suspend fun findUserByUserName(username:String):Flow<List<User>?>

        suspend fun  createAccount(user: User):String
        suspend fun  updateAccount(user: User):Boolean

        suspend fun sendFriendRequest (userName:String,friendUserName:String):ResponseResult
         fun  findUserWithUID( uid: String): Flow<User?>
         fun findListFriendRequestWithUID( uid: String):Flow<List<User>?>
         fun findListFriendWithUID( uid: String):Flow<List<User>?>
        suspend fun acceptFriendRequest (userName:String,friendUserName:String):String
        suspend fun schedulePushNotification(dataSecretMessage: DataSecretMessage):ResponseResult
        fun getScheduleMessage(uid:String):Flow<List<DataSecretMessage>?>
        fun getSentMessage(userName:String):Flow<List<DataSecretMessage>?>
        fun getCanceledMessage(userName:String):Flow<List<DataSecretMessage>?>


    }
    interface  LocalDataSource{

    }
}