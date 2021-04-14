package com.alarm.momentix.services

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
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

class AlarmService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var vibrator: Vibrator
    private lateinit var ringtone: Uri
    private lateinit var alarm: Alarm

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.isLooping = true
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
                    alarmTitle = alarm.title
                    try {
                        mediaPlayer.setDataSource(this.baseContext, Uri.parse(alarm.tone))
                        mediaPlayer.prepareAsync()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    try {
                        mediaPlayer.setDataSource(this.baseContext, ringtone)
                        mediaPlayer.prepareAsync()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                val stopIntent = Intent(this, AlarmService::class.java).apply {
                    action = Constants.STOP_SERVICE
                }
                val pStopIntent = PendingIntent.getService(this, 100, stopIntent, 0)

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
                    .addAction(R.drawable.ic_baseline_repeat_24, "Snooze", null)
                    .build()

                mediaPlayer.setOnPreparedListener {
                    it.start()
                }

                if (alarm.vibrate) {
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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.start()
        vibrator.cancel()
    }

}