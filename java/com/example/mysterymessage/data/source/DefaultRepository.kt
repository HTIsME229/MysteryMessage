package com.example.mysterymessage.data.source

import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.data.source.remote.ResponseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultRepository
@Inject constructor(
    private val localDataSource: DataSource.LocalDataSource,
    private val remoteDataSource: DataSource.RemoteDataSource
):Repository.RemoteRepository {


    override suspend fun createAccount(user: User): String {
        return remoteDataSource.createAccount(user)
    }

    override suspend fun updateAccount(user: User): Boolean {
        return remoteDataSource.updateAccount(user)
    }

    override suspend fun findUserByUserName(username: String): Flow<List<User>?> {
        return remoteDataSource.findUserByUserName(username)
    }

    override suspend fun sendFriendRequest(
        userName: String,
        friendUserName: String
    ): ResponseResult {
        return remoteDataSource.sendFriendRequest(userName,friendUserName)
    }
    override suspend fun  findUserWithUID(uid: String): Flow<User?>{
        return remoteDataSource.findUserWithUID(uid)
    }

    override  fun findListFriendRequestWithUID(uid: String): Flow<List<User>?> {
       return remoteDataSource.findListFriendRequestWithUID(uid)
    }

    override fun findListFriendWithUID(uid: String): Flow<List<User>?> {
        return remoteDataSource.findListFriendWithUID(uid)
    }

    override suspend fun acceptFriendRequest(userName: String, friendUserName: String): String {
        return remoteDataSource.acceptFriendRequest(userName,friendUserName)
    }

    override suspend fun schedulePushNotification(dataSecretMessage: DataSecretMessage): ResponseResult {
        return remoteDataSource.schedulePushNotification(dataSecretMessage)
    }

    override fun getScheduleMessage(uid: String): Flow<List<DataSecretMessage>?> {
        return remoteDataSource.getScheduleMessage(uid)
    }

    override fun getSentMessage(userName: String): Flow<List<DataSecretMessage>?> {
        return remoteDataSource.getSentMessage(userName)
    }

    override fun getCanceledMessage(userName: String): Flow<List<DataSecretMessage>?> {
return remoteDataSource.getCanceledMessage(userName)   }

}