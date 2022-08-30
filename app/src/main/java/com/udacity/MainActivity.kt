package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*


class MainActivity : AppCompatActivity() {
    private var downloadID: Long = 0
    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main)
        setSupportActionBar(toolbar)
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
        custom_button.setButtonState(ButtonState.Loading)
            download()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val custom =
                downloadManager.query(id?.let { DownloadManager.Query().setFilterById(it) })
            if (custom.moveToFirst()) {
                val status = custom.getInt(custom.getColumnIndex(DownloadManager.COLUMN_STATUS))
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    Notification.showNotification(URL, "STATUS_SUCCESSFUL", this@MainActivity)
                }
            }

        }
    }

    private fun download() {
        if (URL != "none") {
            val request =
                DownloadManager.Request(Uri.parse(URL))
                    .setTitle(getString(R.string.app_name))
                    .setDescription(getString(R.string.app_description))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS.toString(),
                        "/" + "load_app"
                    )

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            downloadID =
                downloadManager.enqueue(request)// enqueue puts the download request in the queue.
        } else {
            Toast.makeText(this, "Please choose option", Toast.LENGTH_SHORT).show()
        }

    }


    companion object {
        var URL = "none"
        private const val CHANNEL_ID = "channelId"
    }

    fun buttonDownloadAction(view: View) {
        when (view.id) {
            R.id.radioButton4 -> {
                URL = "https://github.com/bumptech/glide/archive/master.zip"
            }
            R.id.radioButton3 -> {
                URL =
                    "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
            }
            R.id.radioButton2 -> {
                URL = "https://github.com/square/retrofit/archive/master.zip"
            }
            else -> {
                URL = "none"
            }

        }
    }

//    fun customButtonDownload(view: View) {
//       // view.custom_button.
//        download()
//
//
//    }


}
