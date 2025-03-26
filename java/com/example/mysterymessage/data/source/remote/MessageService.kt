package com.example.mysterymessage.data.source.remote

import com.example.mysterymessage.data.model.Message
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.ui.AddFriend.DataAddFriend
import com.example.mysterymessage.ui.AddFriend.UsernameRequest
import com.example.mysterymessage.ui.login.LoginData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageService {
    @POST("/")
    suspend fun  createAccount(@Body user: User):Response<ResponseResult>
    @POST("/")
    suspend fun  updateAccount(@Body user: User):Response<ResponseResult>
    @POST("/")
   suspend fun login(@Body user: LoginData):Response<User?>
    @POST("/")
    suspend fun sendMessage(@Body message: Message):Response<ResponseResult>
    @POST("/")
    suspend fun findUserByUserName(@Body username:UsernameRequest): Response<List<User>?>
    @POST("/")
    suspend fun sendFriendRequest (@Body dataAddFriend: DataAddFriend):Response<ResponseResult>
    @POST("/")
    suspend fun findUserWithUID(@Body request: Map<String, String>): Response<User?>
    @POST("/")
    suspend fun findListFriendRequestWithUID(@Body request: Map<String, String>):Response<List<User>?>
    @POST("/")
    suspend fun findListFriendWithUID(@Body request: Map<String, String>):Response<List<User>?>
    @POST("/")
    suspend fun acceptFriendRequest(@Body dataAddFriend: DataAddFriend):Response<String>
    @POST("/schedulePushNotification")
    suspend fun schedulePushNotification(@Body dataSecretMessage: DataSecretMessage):Response<ResponseResult>
    @POST("/")
    suspend fun getMessage(@Body request: Map<String, String>):Response<List<DataSecretMessage>?>
    @POST("/")
    suspend fun getSentMessage(@Body request: Map<String, String>):Response<List<DataSecretMessage>?>
    @POST("/")
    suspend fun getLetterToYou(@Body request: Map<String, String>):Response<List<DataSecretMessage>?>
    @POST("/")
    suspend fun getCanceledMessage(@Body request: Map<String, String>):Response<List<DataSecretMessage>?>
}