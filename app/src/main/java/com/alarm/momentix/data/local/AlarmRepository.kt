package com.alarm.momentix.data.local

import androidx.lifecycle.LiveData
import com.alarm.momentix.data.model.Alarm
import javax.inject.Inject

class AlarmRepository
@Inject constructor(val dao: AlarmDao) {


    fun insert(alarm: Alarm) {
        dao.insert(alarm)
    }

    fun update(alarm: Alarm) {
        dao.updateAlarm(alarm)
    }

    fun getAllAlarm(): LiveData<List<Alarm>> {
        return dao.getAlarms()
    }

    fun deleteAlarm(alarmId: Int) {
        dao.delete(alarmId)
    }

    fun deleteAll() {
        dao.deleteAll()
    }


}