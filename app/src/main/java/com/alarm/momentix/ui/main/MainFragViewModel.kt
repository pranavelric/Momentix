package com.alarm.momentix.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alarm.momentix.data.local.AlarmRepository
import com.alarm.momentix.data.model.Alarm
import kotlinx.coroutines.launch
//import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
class MainFragViewModel @ViewModelInject constructor(private val repository: AlarmRepository) :
    ViewModel() {

    private var _alarmList = MutableLiveData<List<Alarm>>()
    val alarmList: LiveData<List<Alarm>> = _alarmList


    fun getAlarms() = viewModelScope.launch {
//        _alarmList.value = repository.getAllAlarm().value
        _alarmList.postValue(repository.getAllAlarm().value)
    }

    fun update(alarm: Alarm) = viewModelScope.launch {
        repository.update(alarm)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun deleteAlarm(alarmId: Int)= viewModelScope.launch  {
        repository.deleteAlarm(alarmId)

    }

    fun insertAlarm(alarm: Alarm) = viewModelScope.launch {
        repository.insert(alarm)
    }


}