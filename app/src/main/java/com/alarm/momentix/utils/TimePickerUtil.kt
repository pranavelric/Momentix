package com.alarm.momentix.utils

import android.os.Build
import android.widget.TimePicker
import java.sql.Time
import java.text.SimpleDateFormat

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

        fun getFormattedTime(hour:Int,minute:Int): String {
//            val hour = getTimePickerHour(tp)
//            val minute = getTimePickerMinute(tp)
            val time = Time(hour, minute, 0)
            val simpleDateFormat = SimpleDateFormat("h:mma")
            return  simpleDateFormat.format(time)
        }

    }

}