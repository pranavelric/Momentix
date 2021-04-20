package com.alarm.momentix.utils

import android.os.Build
import android.widget.TimePicker
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TimePickerUtil {

    companion object {

        fun getTimePickerHour(tp: TimePicker): Int {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                return tp.hour
            } else {
                return tp.currentHour
            }
        }

        fun getTimePickerMinute(tp: TimePicker): Int {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                return tp.minute
            } else {
                return tp.currentMinute
            }
        }

        fun getFormattedTime(hour: Int, minute: Int): String {
            val time = Time(hour, minute, 0)
            val simpleDateFormat = SimpleDateFormat("h:mma")
            return simpleDateFormat.format(time)
        }

        fun getCurrentFormattedDate(): String {
            var formatted = ""
            return if (checkAboveOreo()) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd")
                formatted = current.format(formatter)
                formatted
            } else {
                formatted = SimpleDateFormat.getDateInstance().format(Date())
                formatted
            }

        }

    }

}