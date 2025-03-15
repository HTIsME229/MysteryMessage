package com.example.mysterymessage.data.source

import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.source.remote.ResponseResult
import com.example.mysterymessage.ui.login.LoginData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
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
}