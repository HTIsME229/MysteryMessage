package com.example.mysterymessage.data.source.remote

import com.example.mysterymessage.data.model.Message
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.ui.AddFriend.DataAddFriend
import com.example.mysterymessage.ui.AddFriend.UsernameRequest
import com.example.mysterymessage.ui.login.LoginData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

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

}