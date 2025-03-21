package com.example.mysterymessage.data.source.remote

import android.util.Log
import com.example.mysterymessage.data.model.dto.DataSecretMessage
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.Gson
import kotlin.coroutines.suspendCoroutine

class ScheduleNotification {
      fun scheduleNotification(
  dataSecretMessage: DataSecretMessage,
        onResult: (ResponseResult) -> Unit
    ) {
        val functions = FirebaseFunctions.getInstance()
          val gson = Gson()

        val data = hashMapOf(
            "userReceiverId" to dataSecretMessage.userReceiverId,
            "userNameSender" to dataSecretMessage.userNameSender,
            "token" to dataSecretMessage.token,
            "message" to dataSecretMessage.message,
            "sendTime" to dataSecretMessage.sendTime,
            "title" to dataSecretMessage.title,
            "dataQuestion" to (gson.toJson(dataSecretMessage.dataQuestion ?: ""))
        )

        functions.getHttpsCallable("schedulePushNotification")
            .call(data)
            .addOnSuccessListener { it ->
                Log.d("ScheduleNotification", "scheduleNotification: ${it.data}")
                onResult(ResponseResult(true, null))
            }
            .addOnFailureListener { e ->
                Log.e("ScheduleNotification", "scheduleNotification: ${e.message}")
                onResult(ResponseResult(false, e.message))
            }
    }
}