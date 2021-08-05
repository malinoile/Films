package ru.malinoil.films

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, context?.getString(R.string.change_connectivity), Toast.LENGTH_LONG)
            .show()
    }
}