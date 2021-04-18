package com.alarm.momentix.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alarm.momentix.data.model.Alarm
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository
@Inject constructor(val dao: AlarmDao) {


    suspend fun insert(alarm: Alarm) {
        dao.insert(alarm)
    }

    suspend fun update(alarm: Alarm) {
        dao.updateAlarm(alarm)
    }
    
    fun getAllLiveAlarm() = dao.getLiveAlarms()

    suspend fun deleteAlarm(alarmId: Int) {
        dao.delete(alarmId)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }


}