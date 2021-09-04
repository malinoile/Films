package ru.malinoil.films.model

import android.content.Context
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import ru.malinoil.films.R

class NotificationHelper {
    companion object {
        const val MOVIE_CHANNEL_ID = "movie"
        const val FIREBASE_NOTIFICATION_ID = 1
        const val CLICK_NOTIFICATION_ID = 2

        fun initNotificationChannels(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = NotificationManagerCompat.from(context)
                val channel = NotificationChannelCompat.Builder(
                    MOVIE_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_DEFAULT
                )
                    .setName(context.getString(R.string.notification_movie_channel_name))
                    .setDescription(context.getString(R.string.notification_movie_channel_description))
                    .build()
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}