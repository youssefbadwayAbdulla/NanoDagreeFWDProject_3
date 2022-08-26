package com.udacity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notification() {
    companion object {
        const val CHANNEL_ID = "channel_id "
        const val NOTIFICATION_ID = 1
        fun showNotification(downloadFileName: String, status: String,context: Context) {
            val intent = Intent(context,DetailActivity::class.java).apply {
            // flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("fileName", downloadFileName)
                putExtra("status", status)

            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, 0
            )
            // TODO: Step 2.0 add style
            val eggImage = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.ic_assistant_black_24dp
            )
            val bigPicStyle = NotificationCompat.BigPictureStyle()
                .bigPicture(eggImage)
                .bigLargeIcon(null)

            // NotificationUtils.kt


            val notification = NotificationCompat.Builder(
                context, Notification.CHANNEL_ID
            )
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("New Task")
                .setContentText("Subscribe on the channel")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                // NotificationUtils.kt
                // TODO: Step 2.1 add style to builder
                .setStyle(bigPicStyle)
                .setLargeIcon(eggImage)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_assistant_black_24dp, "applicationContext", pendingIntent)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelName = "Channel Name"
                val channelDescription = "Channel Description"
                val channelImportance = NotificationManager.IMPORTANCE_HIGH

                val channel = NotificationChannel(Notification.CHANNEL_ID, channelName, channelImportance).apply {
                    description = channelDescription
                }
                val notificationManager = context.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            with(NotificationManagerCompat.from(context)) {
                notify(Notification.NOTIFICATION_ID, notification.build())
            }
        }
    }

}