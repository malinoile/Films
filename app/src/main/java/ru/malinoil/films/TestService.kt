package ru.malinoil.films

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class TestService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        for (i in 0..100) {
            Log.d("@@@", i.toString())
        }
        stopSelf()
        Toast.makeText(this, "Сервис завершил свою работу", Toast.LENGTH_SHORT).show()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}