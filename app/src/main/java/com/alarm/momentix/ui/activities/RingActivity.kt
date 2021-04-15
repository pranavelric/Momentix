package com.alarm.momentix.ui.activities

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alarm.momentix.data.model.Alarm
import com.alarm.momentix.databinding.ActivityRingBinding
import com.alarm.momentix.services.AlarmService
import com.alarm.momentix.ui.main.MainFragViewModel
import com.alarm.momentix.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class RingActivity : AppCompatActivity() {

    private var alarm: Alarm? = null
    private val mainFragViewModel: MainFragViewModel by lazy {
        ViewModelProvider(this).get(MainFragViewModel::class.java)
    }

    private lateinit var binding: ActivityRingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }
        val bundle = intent.getBundleExtra(Constants.BUNDLE_ALARM_OBJ)
        if (bundle != null) {
            if (bundle.getSerializable(Constants.BUNDLE_ALARM_OBJ) != null)
                alarm = (bundle.getSerializable(Constants.BUNDLE_ALARM_OBJ) as? Alarm)!!
        }

        binding.activityRingDismiss.setOnClickListener {
            dismissAlarm()
        }
        binding.activityRingSnooze.setOnClickListener {
            snoozeAlarm()
        }

        animateClock()

    }

    private fun animateClock() {
        val rotateAnimation =
            ObjectAnimator.ofFloat(binding.activityRingClock, "rotation", 0f, 30f, 0f, -30f, 0f)
        rotateAnimation.repeatCount = ValueAnimator.INFINITE
        rotateAnimation.duration = 800
        rotateAnimation.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(false)
            setTurnScreenOn(false)
        } else {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }
    }

    private fun snoozeAlarm() {

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MINUTE, 5)

        if (alarm != null) {
            alarm!!.hour = calendar.get(Calendar.HOUR_OF_DAY)
            alarm!!.minute = calendar.get(Calendar.MINUTE)
            alarm!!.title = "Snooze ${alarm!!.title}"
        } else {

            alarm = Alarm(
                Random().nextInt(Integer.MAX_VALUE),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "Snooze",
                RingtoneManager.getActualDefaultRingtoneUri(
                    baseContext,
                    RingtoneManager.TYPE_ALARM
                ).toString(),
                false
            )


        }

        alarm?.schedule(applicationContext)

        val intentService = Intent(applicationContext, AlarmService::class.java)
        applicationContext.stopService(intentService)
        finish()

    }

    private fun dismissAlarm() {

        if (alarm != null) {
            alarm!!.started = false
            alarm!!.cancelAlarm(baseContext)
            mainFragViewModel.update(alarm!!)
        }
        val intentService = Intent(applicationContext, AlarmService::class.java)
        applicationContext.stopService(intentService)
        finish()

    }


}