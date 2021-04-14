package com.alarm.momentix.services

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alarm.momentix.data.local.AlarmRepository
import com.alarm.momentix.ui.main.MainFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RescheduleAlarmService : LifecycleService() {

    @Inject
    lateinit var repository: AlarmRepository


    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        lifecycleScope.launch {
            repository.getAllAlarmLiveData().observe(this@RescheduleAlarmService, { list ->
                for (a in list) {
                    if (a.started) {
                        a.schedule(applicationContext)
                    }
                }
            })
        }



        return START_STICKY
    }
}