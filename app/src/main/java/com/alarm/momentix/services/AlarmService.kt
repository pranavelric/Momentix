package com.alarm.momentix.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AlarmService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}