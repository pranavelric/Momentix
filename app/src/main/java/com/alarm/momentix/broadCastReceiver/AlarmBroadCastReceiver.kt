package com.alarm.momentix.broadCastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.alarm.momentix.data.model.Alarm
import com.alarm.momentix.services.AlarmService
import com.alarm.momentix.services.RescheduleAlarmService
import com.alarm.momentix.utils.Constants
import com.alarm.momentix.utils.Constants.ALARM_OBJ
import com.alarm.momentix.utils.Constants.BUNDLE_ALARM_OBJ
import com.alarm.momentix.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class AlarmBroadCastReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context?, intent: Intent?) {


        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                startRescheduleAlarmService(context)
                context?.toast("Alarm Reboot")

            }

            else -> {
                val bundle = intent?.getBundleExtra(BUNDLE_ALARM_OBJ)
                if (bundle != null) {
                    val alarm = bundle.getSerializable(ALARM_OBJ) as Alarm
                    context?.toast("Alarm Received")

                    if (alarm != null) {
                        if (!alarm.recurring) {
                            startAlarmService(context, alarm)
                        } else {
                            if (isAlarmToday(alarm)) {
                                startAlarmService(context, alarm)
                            }
                        }
                    }

                }
            }


        }


    }

    private fun isAlarmToday(alarm: Alarm): Boolean {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val today = calendar.get(Calendar.DAY_OF_WEEK)
        when (today) {
            Calendar.MONDAY -> {
                if (alarm.monday)
                    return true
                return false
            }
            Calendar.TUESDAY -> {
                if (alarm.tuesday)
                    return true
                return false
            }
            Calendar.WEDNESDAY -> {
                if (alarm.wednesday)
                    return true
                return false
            }
            Calendar.THURSDAY -> {
                if (alarm.thursday)
                    return true
                return false
            }
            Calendar.FRIDAY -> {
                if (alarm.friday)
                    return true
                return false
            }
            Calendar.SATURDAY -> {
                if (alarm.saturday)
                    return true
                return false
            }
            Calendar.SUNDAY -> {
                if (alarm.sunday)
                    return true
                return false
            }
            else -> {
                return false
            }

        }

    }


    private fun startAlarmService(context: Context?, alarm: Alarm) {

        val bundle = Bundle().apply {
            putSerializable(ALARM_OBJ, alarm)

        }


        val intent = Intent(context, AlarmService::class.java).apply {
            putExtra(BUNDLE_ALARM_OBJ, bundle)
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            context?.startForegroundService(intent)

        } else {
            context?.startService(intent)
        }
    }

    private fun startRescheduleAlarmService(context: Context?) {

        val intent = Intent(context, RescheduleAlarmService::class.java)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            context?.startForegroundService(intent)
        } else {
            context?.startService(intent)
        }

    }
}