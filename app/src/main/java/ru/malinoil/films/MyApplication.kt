package ru.malinoil.films

import android.app.Application
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.malinoil.films.model.NotificationHelper
import ru.malinoil.films.model.repositories.impls.room.FilmsDatabase

private const val BASE_URL = "https://api.themoviedb.org"
private const val DATABASE_NAME = "films_db"

class MyApplication : Application() {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val database by lazy {
        Room.databaseBuilder(applicationContext, FilmsDatabase::class.java, DATABASE_NAME)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.initNotificationChannels(this)
    }
}