package com.alarm.momentix.ui.createAlarm

import android.app.Activity
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alarm.momentix.R
import com.alarm.momentix.data.model.Alarm
import com.alarm.momentix.databinding.FragmentCreateAlarmBinding
import com.alarm.momentix.utils.TimePickerUtil
import com.alarm.momentix.utils.gone
import com.alarm.momentix.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random


private const val ARG_ALARM = "alarm"

@AndroidEntryPoint
class CreateAlarmFragment : Fragment() {

    private var alarm: Alarm? = null
    lateinit var binding: FragmentCreateAlarmBinding
    private val createAlarmViewModel: CreateAlarmViewModel by lazy {
        ViewModelProvider(this).get(CreateAlarmViewModel::class.java)
    }

    private var isVibrate: Boolean = false
    lateinit var tone: String
    lateinit var ringtone: Ringtone


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alarm = it.getSerializable(ARG_ALARM) as Alarm
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAlarmBinding.inflate(layoutInflater, container, false)
        tone = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM)
            .toString()
        ringtone = RingtoneManager.getRingtone(context, Uri.parse(tone))
        setData()
        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {

        binding.fragmentCreatealarmRecurring.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.fragmentCreatealarmRecurringOptions.visible()

            } else {
                binding.fragmentCreatealarmRecurringOptions.gone()
            }

        }

        binding.fragmentCreatealarmScheduleAlarm.setOnClickListener {
            if (alarm != null) {
                updateAlarm()
            } else {
                scheduleAlarm()
            }
            findNavController().navigateUp()
        }

        binding.fragmentCreatealarmCardSound.setOnClickListener {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
                putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
                putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select alert sound for alarm")
                putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(tone))
                startActivityForResult(this, 5)
            }
        }
        binding.fragmentCreatealarmVibrateSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            isVibrate = isChecked

        }
//        binding.fragmentCreatealarmTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
//
//
//
//        }

        binding.fragmentCreatealarmCancelAlarm.setOnClickListener {

            findNavController().navigateUp()
        }

    }

    private fun scheduleAlarm() {

        val alarmTitle: String = (binding.alarmTitle.text ?: "My Alarm").toString()
        val alarmId = Random(System.currentTimeMillis()).nextInt(Int.MAX_VALUE)
        val alarm = Alarm(
            alarmId = alarmId,
            hour = TimePickerUtil.getTimePickerHour(binding.fragmentCreatealarmTimePicker),
            minute = TimePickerUtil.getTimePickerMinute(binding.fragmentCreatealarmTimePicker),
            started = true,
            recurring = binding.fragmentCreatealarmRecurring.isChecked,
            monday = binding.fragmentCreatealarmCheckMon.isChecked,
            tuesday = binding.fragmentCreatealarmCheckTue.isChecked,
            wednesday = binding.fragmentCreatealarmCheckWed.isChecked,
            thursday = binding.fragmentCreatealarmCheckThu.isChecked,
            friday = binding.fragmentCreatealarmCheckFri.isChecked,
            saturday = binding.fragmentCreatealarmCheckSat.isChecked,
            sunday = binding.fragmentCreatealarmCheckSun.isChecked,
            title = alarmTitle,
            tone = tone,
            vibrate = isVibrate
        )
        createAlarmViewModel.insertAlarm(alarm)
        context?.let { alarm.schedule(it) }

    }

    private fun updateAlarm() {

        val alarmTitle: String = (binding.alarmTitle.text).toString()
        val updateAlarm = Alarm(
            alarmId = alarm?.alarmId!!,
            hour = TimePickerUtil.getTimePickerHour(binding.fragmentCreatealarmTimePicker),
            minute = TimePickerUtil.getTimePickerMinute(binding.fragmentCreatealarmTimePicker),
            started = true,
            recurring = binding.fragmentCreatealarmRecurring.isChecked,
            monday = binding.fragmentCreatealarmCheckMon.isChecked,
            tuesday = binding.fragmentCreatealarmCheckTue.isChecked,
            wednesday = binding.fragmentCreatealarmCheckWed.isChecked,
            thursday = binding.fragmentCreatealarmCheckThu.isChecked,
            friday = binding.fragmentCreatealarmCheckFri.isChecked,
            saturday = binding.fragmentCreatealarmCheckSat.isChecked,
            sunday = binding.fragmentCreatealarmCheckSun.isChecked,
            title = alarmTitle,
            tone = tone,
            vibrate = isVibrate
        )

        createAlarmViewModel.updateAlarm(updateAlarm)
        context?.let { updateAlarm.schedule(it) }

    }

    private fun setData() {

        binding.fragmentCreatealarmSetToneName.text = ringtone.getTitle(context)

        if (alarm != null) {
            updateAlarmInfo(alarm!!)
        }

    }

    private fun updateAlarmInfo(alarm: Alarm) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            binding.fragmentCreatealarmTimePicker.hour = alarm.hour
            binding.fragmentCreatealarmTimePicker.minute = alarm.minute

        } else {
            binding.fragmentCreatealarmTimePicker.currentHour = alarm.hour
            binding.fragmentCreatealarmTimePicker.currentMinute = alarm.minute
        }
        if (alarm.recurring) {
            binding.fragmentCreatealarmRecurring.isChecked = true
            binding.fragmentCreatealarmRecurringOptions.visible()

            if (alarm.monday) {
                binding.fragmentCreatealarmCheckMon.isChecked = true
            }
            if (alarm.tuesday) {
                binding.fragmentCreatealarmCheckTue.isChecked = true
            }
            if (alarm.wednesday) {
                binding.fragmentCreatealarmCheckWed.isChecked = true
            }
            if (alarm.thursday) {
                binding.fragmentCreatealarmCheckThu.isChecked = true
            }
            if (alarm.friday) {
                binding.fragmentCreatealarmCheckFri.isChecked = true
            }
            if (alarm.saturday) {
                binding.fragmentCreatealarmCheckSat.isChecked = true
            }
            if (alarm.sunday) {
                binding.fragmentCreatealarmCheckSun.isChecked = true
            }
        }
        tone = alarm.tone

        ringtone = RingtoneManager.getRingtone(context, Uri.parse(tone))
        Log.d("RRR", "updateAlarmInfo: ${tone} ${ringtone.getTitle(context)}")
        binding.fragmentCreatealarmSetToneName.text = ringtone.getTitle(context)
        if (alarm.vibrate)
            binding.fragmentCreatealarmVibrateSwitch.isChecked = true
        binding.alarmTitle.setText(alarm.title)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            val uri: Uri? = data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            ringtone = RingtoneManager.getRingtone(context, uri)
            val title = ringtone.getTitle(context)
            if (uri != null) {
                tone = uri.toString()
                if (!title.isNullOrEmpty()) {
                    binding.fragmentCreatealarmSetToneName.text = title
                }
            } else {
                binding.fragmentCreatealarmSetToneName.text = ""
            }
        }
    }

}