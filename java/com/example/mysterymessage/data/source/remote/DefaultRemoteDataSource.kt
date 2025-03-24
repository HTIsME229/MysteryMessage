package com.example.mysterymessage.data.source.remote

import android.util.Log
import com.example.mysterymessage.data.model.User
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.example.mysterymessage.data.source.DataSource
import com.example.mysterymessage.ui.AddFriend.DataAddFriend
import com.example.mysterymessage.ui.AddFriend.UsernameRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DefaultRemoteDataSource :DataSource.RemoteDataSource {
    override suspend fun findUserByUserName(username: String): Flow<List<User>?> = flow {
        val baseUrl = "https://findfriendbyusername-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
            val usernameRequest = UsernameRequest(username)
        try {
            val response = retrofit.findUserByUserName(usernameRequest) // Gọi API
            if (response.isSuccessful) {
                emit(response.body()) // Trả về dữ liệu nếu thành công
            } else {
                emit(null) // Trả về `null` nếu API lỗi
            }
        } catch (e: Exception) {
            emit(null)
            e.message?.let { Log.d("call api ", it) }// Bắt lỗi nếu có exception
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun createAccount(user: User): String {
        val baseUrl = "https://adduser-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)

        return try {

            val result = retrofit.createAccount(user)


            if (result.isSuccessful) {
                if(result.body()?.success != false)
                return "Success"
                else
                    return  result.body()?.error?:"Unknown error"

            }

            val errorBody = result.body()

            return errorBody?.error ?: "Unknown error"
        } catch (e: Exception) {
            return "Exception: ${e.message}"
        }
    }
    override suspend fun updateAccount(user: User):Boolean {
        val baseUrl ="https://updateuser-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
        val result = retrofit.updateAccount(user)
        if(result.isSuccessful ){
            return true
        }
        return false
    }


    override suspend fun sendFriendRequest(
        userName: String,
        friendUserName: String
    ): ResponseResult {
        Log.d("CheckLogCallfriend", "Before API call send friend")

        val baseUrl = "https://sendfriendrequest-n6eo3bsn3a-uc.a.run.app"
        val dataAddFriend = DataAddFriend(userName,friendUserName)
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)

        try {
            val response = retrofit.sendFriendRequest(dataAddFriend)
            if(response.isSuccessful){
                return response.body()!!
            }
            else{
                return ResponseResult(success = false, error = "Server Err")
            }
        }
        catch (e:Exception){
            return ResponseResult(success = false, error = e.message)

        }
    }

    override fun findUserWithUID(uid: String): Flow<User?> = flow {
        val baseUrl = "https://finduserwithuid-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
        val body = mapOf<String,String>(
            "uid" to uid
        )
        try {
            val response = retrofit.findUserWithUID(body) // Thực thi request đồng bộ
            Log.d("CheckLogCall", "After API call")

            if (response.isSuccessful) {
                emit(response.body()) // Phát dữ liệu nếu thành công
            } else {
                emit(null) // Xử lý lỗi API
            }
        } catch (e: Exception) {
            Log.e("CheckLogCall", "API call failed", e)
            emit(null) // Xử lý lỗi mạng hoặc ngoại lệ
        }
    }.flowOn(Dispatchers.IO)

    override fun findListFriendRequestWithUID(uid: String): Flow<List<User>?> = flow {
        val baseUrl = "https://findlistfriendrequestbyuid-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
        val body = mapOf<String,String>(
            "uid" to uid
        )
        try {
            val response = retrofit.findListFriendRequestWithUID(body) // Gọi API
            if (response.isSuccessful) {
                emit(response.body()) // Trả về dữ liệu nếu thành công
            } else {
                emit(null) // Trả về `null` nếu API lỗi
            }
        } catch (e: Exception) {
            emit(null)
            e.message?.let { Log.d("call api ", it) }// Bắt lỗi nếu có exception
        }
    }.flowOn(Dispatchers.IO)

    override fun findListFriendWithUID(uid: String): Flow<List<User>?> = flow {
        val baseUrl = "https://findlistfriendbyuid-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
        val body = mapOf<String,String>(
            "uid" to uid
        )
        try {
            val response = retrofit.findListFriendWithUID(body) // Gọi API
            if (response.isSuccessful) {
                emit(response.body()) // Trả về dữ liệu nếu thành công
            } else {
                emit(null) // Trả về `null` nếu API lỗi
            }
        } catch (e: Exception) {
            emit(null)
            e.message?.let { Log.d("call api ", it) }// Bắt lỗi nếu có exception
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun acceptFriendRequest(userName: String, friendUserName: String): String {

        val baseUrl = "https://acceptfriendrequest-n6eo3bsn3a-uc.a.run.app"
        val dataAddFriend = DataAddFriend(userName,friendUserName)
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)

        try {
            val response = retrofit.acceptFriendRequest(dataAddFriend)
            if(response.isSuccessful){
                return response.body()!!
            }
            else{
                return response.body()!!
            }
        }
        catch (e:Exception){
            return e.message.let {
                it?:""
            }

        }
    }

    override suspend fun schedulePushNotification(dataSecretMessage: DataSecretMessage): ResponseResult {
        val baseUrl = "https://schedulepushnotification-n6eo3bsn3a-uc.a.run.app"

        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
        val result = retrofit.schedulePushNotification(dataSecretMessage)
        if(result.isSuccessful){
            return result.body()!!
        }
        else{
            return ResponseResult(success = false, error = "server error")
        }
    }

    override fun getScheduleMessage(uid: String): Flow<List<DataSecretMessage>?> {
        val baseUrl = "https://getmessageinscheduled-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
        val body = mapOf<String,String>(
            "userName" to uid
        )
        return flow {
            try {
                val response = retrofit.getScheduleMessage(body) // Thực thi request đồng bộ
                if (response.isSuccessful) {
                    emit(response.body()) // Phát dữ liệu nếu thành công
                } else {
                    emit(null) // Xử lý lỗi API
                }
            } catch (e: Exception) {
                emit(null) // Xử lý lỗi mạng hoặc ngoại lệ
            }
        }.flowOn(Dispatchers.IO)

    }

    override fun getSentMessage(userName: String): Flow<List<DataSecretMessage>?> {
        val baseUrl = "https://getmessageinsent-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
        val body = mapOf<String,String>(
            "userName" to userName
        )
        return flow {
            try {
                val response = retrofit.getSentMessage(body) // Thực thi request đồng bộ
                if (response.isSuccessful) {
                    emit(response.body()) // Phát dữ liệu nếu thành công
                } else {
                    emit(null) // Xử lý lỗi API
                }
            } catch (e: Exception) {
                emit(null) // Xử lý lỗi mạng hoặc ngoại lệ
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getLetterToYou(uid: String): Flow<List<DataSecretMessage>?> {
        val baseUrl = "https://getmessagesenttoyou-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
        val body = mapOf<String,String>(
            "uid" to uid
        )
        return flow {
            try {
                val response = retrofit.getLetterToYou(body) // Thực thi request đồng bộ
                if (response.isSuccessful) {
                    emit(response.body()) // Phát dữ liệu nếu thành công
                } else {
                    emit(null) // Xử lý lỗi API
                }
            } catch (e: Exception) {
                emit(null) // Xử lý lỗi mạng hoặc ngoại lệ
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getCanceledMessage(userName: String): Flow<List<DataSecretMessage>?> {
        val baseUrl = "https://getmessageincancelled-n6eo3bsn3a-uc.a.run.app"
        val retrofit = createRetrofitService(baseUrl).create(MessageService::class.java)
        val body = mapOf<String,String>(
            "userName" to userName
        )
        return flow {
            try {
                val response = retrofit.getCanceledMessage(body) // Thực thi request đồng bộ
                if (response.isSuccessful) {
                    emit(response.body()) // Phát dữ liệu nếu thành công
                } else {
                    emit(null) // Xử lý lỗi API
                }
            } catch (e: Exception) {
                emit(null) // Xử lý lỗi mạng hoặc ngoại lệ
            }
        }.flowOn(Dispatchers.IO)
    }


    private fun createRetrofitService(baseUrl: String): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // Log toàn bộ request/response
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)  // Gắn `client` vào Retrofit
            .build()
    }
}
