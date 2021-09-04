package ru.malinoil.films

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.malinoil.films.model.NotificationHelper

private const val REQUEST_CODE = 1

class FirebasePushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        Log.d("@@@", "onNewToken: messagingService")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("@@@", "onMessageReceived: messagingService")
        val pendingIntent = PendingIntent.getActivity(
            this,
            REQUEST_CODE,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(this, NotificationHelper.MOVIE_CHANNEL_ID)
            .setContentTitle(message.notification?.title)
            .setSubText(message.notification?.body)
            .setSmallIcon(R.drawable.ic_notification_movie)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(NotificationHelper.FIREBASE_NOTIFICATION_ID, notification)
    }
}