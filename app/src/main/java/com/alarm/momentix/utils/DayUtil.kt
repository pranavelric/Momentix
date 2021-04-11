package com.alarm.momentix.utils

import java.util.*

class DayUtil {

    companion object {
        fun toDay(day: Int): String {

            when (day) {
                Calendar.SUNDAY -> {
                    return "Sunday"
                }
                Calendar.MONDAY -> {
                    return "Monday"
                }
                Calendar.TUESDAY -> {
                    return "Tuesday"
                }
                Calendar.WEDNESDAY -> {
                    return "Wednesday"
                }
                Calendar.THURSDAY -> {
                    return "Thursday"
                }
                Calendar.FRIDAY -> {
                    return "Friday"
                }
                Calendar.SATURDAY -> {
                    return "Saturday"
                }
                else -> {
                    return ""
                }

            }
        }

        fun getDay(hour: Int, minute: Int): String {
            val calendar = Calendar.getInstance()
//            calendar.timeInMillis=System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                return "Tommorow"
            } else {
                return "Today"
            }

        }

    }
}