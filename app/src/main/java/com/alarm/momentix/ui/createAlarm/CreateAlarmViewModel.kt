package com.alarm.momentix.ui.createAlarm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alarm.momentix.data.local.AlarmRepository
import com.alarm.momentix.data.model.Alarm
import kotlinx.coroutines.launch

class CreateAlarmViewModel @ViewModelInject constructor(private val repository: AlarmRepository) :
    ViewModel() {


    fun insertAlarm(alarm: Alarm) = viewModelScope.launch {
        repository.insert(alarm)
    }

    fun updateAlarm(alarm: Alarm) = viewModelScope.launch {
        repository.update(alarm)
    }


}