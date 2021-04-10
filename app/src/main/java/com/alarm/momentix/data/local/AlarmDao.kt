package com.alarm.momentix.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alarm.momentix.data.model.Alarm

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarm: Alarm)

    @Query("DELETE FROM alarm_table")
    fun deleteAll()

    @Query("SELECT * FROM alarm_table ORDER BY alarmId ASC")
    fun getAlarms(): LiveData<List<Alarm>>

    @Update
    fun updateAlarm(alarm: Alarm)

    @Query("DELETE FROM alarm_table WHERE alarmId= :alarmID")
    fun delete(alarmID: Int)

}