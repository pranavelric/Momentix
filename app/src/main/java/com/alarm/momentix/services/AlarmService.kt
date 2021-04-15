package com.alarm.momentix.services

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.media.VolumeShaper
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.alarm.momentix.R
import com.alarm.momentix.data.model.Alarm
import com.alarm.momentix.ui.activities.RingActivity
import com.alarm.momentix.utils.Constants
import java.util.*

class AlarmService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var ringtone: Uri
    private var alarm: Alarm? = null

    private lateinit var config:VolumeShaper.Configuration
    private lateinit var volumeShaper: VolumeShaper

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.isLooping = true
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
        )

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
             config = VolumeShaper.Configuration.Builder()
                .setDuration(10000)
                .setCurve(floatArrayOf(0f, 1f), floatArrayOf(0f, 1f))
                .setInterpolatorType(VolumeShaper.Configuration.INTERPOLATOR_TYPE_LINEAR)
                .build()


        }

        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(
            this.baseContext,
            RingtoneManager.TYPE_ALARM
        )

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            Constants.STOP_SERVICE -> {

                stopForeground(false)
                stopSelf()

            }
            Constants.SNOOZE_SERVICE -> {

                snoozeAlarm()


            }
            else -> {

                val bundle = intent?.getBundleExtra(Constants.BUNDLE_ALARM_OBJ)
                if (bundle != null) {
                    alarm = bundle.getSerializable(Constants.ALARM_OBJ) as Alarm
                }
                val notificationIntent = Intent(this, RingActivity::class.java).apply {
                    putExtra(Constants.BUNDLE_ALARM_OBJ, bundle)
                }
                val pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                var alarmTitle = "My Alarm"

                if (alarm != null) {
                    alarmTitle = alarm!!.title
                    try {
                        mediaPlayer.setDataSource(this.baseContext, Uri.parse(alarm!!.tone))
                        mediaPlayer.prepareAsync()

                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                            volumeShaper = mediaPlayer.createVolumeShaper(config)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    try {
                        mediaPlayer.setDataSource(this.baseContext, ringtone)
                        mediaPlayer.prepareAsync()


                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                            volumeShaper = mediaPlayer.createVolumeShaper(config)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                val stopIntent = Intent(this, AlarmService::class.java).apply {
                    action = Constants.STOP_SERVICE
                }
                val pStopIntent = PendingIntent.getService(this, 100, stopIntent, 0)

                val snoozeIntent = Intent(this, AlarmService::class.java).apply {
                    action = Constants.SNOOZE_SERVICE
                }
                val pSnoozeIntent = PendingIntent.getService(this, 100, snoozeIntent, 0)


                //create notification
                val notification = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                    .setContentTitle("Ring Ring .. Ring Ring")
                    .setContentText(alarmTitle)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setSound(null)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setFullScreenIntent(pendingIntent, true)
                    .addAction(R.drawable.ic_baseline_delete_forever_24, "Dismiss", pStopIntent)
                    .addAction(R.drawable.ic_baseline_repeat_24, "Snooze", pSnoozeIntent)
                    .build()
                mediaPlayer.setOnPreparedListener {
                    it.start()

                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                        volumeShaper.apply(VolumeShaper.Operation.PLAY)

                    }

                }

                if (alarm?.vibrate == true) {
                    val pattern = longArrayOf(0, 100, 1000)
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
                    } else {
                        vibrator.vibrate(pattern, 0);
                    }

                }
                startForeground(1, notification)


            }

        }

        return START_STICKY
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

        alarm?.schedule(baseContext)

        stopSelf()
    }


    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer.stop()
        vibrator.cancel()
    }

}