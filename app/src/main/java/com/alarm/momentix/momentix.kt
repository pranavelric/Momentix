package com.alarm.momentix

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.alarm.momentix.utils.Constants
import com.alarm.momentix.utils.Constants.CHANNEL_ID
import com.alarm.momentix.utils.Constants.CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class momentix : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        val sp = getSharedPreferences(Constants.SHARED_PREFRENCE, 0)
        if (sp.getBoolean(Constants.NIGHT_MODE_ENABLED, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }
    }


}