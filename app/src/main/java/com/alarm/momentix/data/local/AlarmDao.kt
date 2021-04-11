package com.alarm.momentix.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alarm.momentix.data.model.Alarm

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarm: Alarm)

    @Query("DELETE FROM alarm_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM alarm_table ORDER BY alarmId ASC")
    suspend fun getAlarms():List<Alarm>


    @Update
    suspend fun updateAlarm(alarm: Alarm)

    @Query("DELETE FROM alarm_table WHERE alarmId= :alarmID")
    suspend fun delete(alarmID: Int)

}