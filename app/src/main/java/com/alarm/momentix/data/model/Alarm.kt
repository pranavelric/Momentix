package com.alarm.momentix.data.model

import android.app.AlarmManager
import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class Alarm(
    @PrimaryKey
    val alarmId: Int?,
    val hour: Int,
    val minute: Int,
    val started: Boolean,
    val recurring: Boolean,
    val monday: Boolean,
    val tuesday: Boolean,
    val wednesday: Boolean,
    val thursday: Boolean,
    val friday: Boolean,
    val saturday: Boolean,
    val sunday: Boolean,
    val title: String,
    val tone: String,
    val vibrate: String,

    ) {
    fun schedule(context: Context) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    }
}