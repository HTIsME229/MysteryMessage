package com.example.mysterymessage

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.mysterymessage.data.model.dto.DataQuestion
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage



@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MessengerService:FirebaseMessagingService() {
    override fun onMessageReceived(@NonNull remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
//        if (remoteMessage.notification != null) {
//            Log.d("FCM", "📢 Firebase tự hiển thị thông báo khi app background.")
//            return
//        }
        val title = if (remoteMessage.notification != null) remoteMessage.notification!!
            .title else "Thông báo mới"
        val message = if (remoteMessage.notification != null) remoteMessage.notification!!
            .body else ""
        // Lấy messageId từ data

        val userReceiverId = remoteMessage.data["userReceiverId"]
        val userNameSender = remoteMessage.data["userNameSender"]
        val dataQuestion :DataQuestion = remoteMessage.data["dataQuestion"]?.let {
            DataQuestion.fromJson(it)
        } ?: DataQuestion();
        Log.d("MessengerService", "onMessageReceived: $dataQuestion + $userReceiverId + $userNameSender ")
        val action = NavGraphDirections.actionGlobalNavSecretMessage(
            title= title?:"",
            message = message?: "",
            userReceiverId = userReceiverId?:"",
            userNameSender = userNameSender?: "",
            dataQuestion = dataQuestion
        )
        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.nav_secret_message)
            .setArguments(action.arguments)
            .createPendingIntent()
        val channelId = "your_channel_id"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Tin nhắn bí ẩn",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // Hiển thị thông báo với PendingIntent
        val notification = NotificationCompat.Builder(this, "your_channel_id")
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Bạn có tin nhắn mới!")
            .setContentText("Nhấn để mở")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        NotificationManagerCompat.from(this).notify(1, notification)




    }
}