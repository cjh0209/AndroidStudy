package com.example.viewandviewgroup

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import layout.Constant

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
       return null
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 버전 확인
            showNotification()
        }

        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        // 알림을 클릭했을 때 이동할 화면을 지정한다.
        val notificationIntent = Intent(this, SecondActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = Notification
                .Builder(this, Constant.CHANNEL_ID)
                .setContentText("현재 음악이 재생 중입니다.")
                .setSmallIcon(R.drawable.ic_flo_logo)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(Constant.MUSIC_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                    Constant.CHANNEL_ID, "Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                    NotificationManager::class.java
            )

            manager.createNotificationChannel(serviceChannel)
        }
    }
}