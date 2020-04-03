package dev.claucookielabs.pasbuk.passdownload.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.common.presentation.utils.isAtLeastO
import dev.claucookielabs.pasbuk.passdownload.presentation.ui.PassDownloadActivity
import dev.claucookielabs.pasbuk.passdownload.services.model.IntentScheme
import org.koin.android.ext.android.inject

class PassDownloadService : IntentService("PassDownloadService") {

    private val intentDataProvider: IntentContentProvider by inject()

    override fun onHandleIntent(intent: Intent?) {
        Log.i("Info", "Download Service onHandleIntent")
        intentDataProvider.parse(intent?.data)?.let {
            startForeground(NOTIFICATION_ID, createNotification(it.filename))
            var intentData = it
            if (it.isInsideServer()) {
                Log.i("Info", "Download Service downloading file")
                intentData = intentDataProvider.downloadFile(it.uri.toString())
            }
            unzipFile(intentData)
        }
    }

    private fun unzipFile(intentScheme: IntentScheme?) {
        Log.i("Info", "Download Service unzip")
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("Info", "Download Service onCreate")
    }

    override fun onDestroy() {
        Log.i("Info", "Download Service onDestroy")
        super.onDestroy()
    }

    private fun createNotification(contentName: String): Notification {
        val title = getString(R.string.downloading_pass)
        val intent = Intent(this, PassDownloadActivity::class.java)
        val builder = NotificationCompat.Builder(this, createNotificationChannel())
            .setContentText(contentName)
            .setContentTitle(title)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
            .setColor(ContextCompat.getColor(this, R.color.colorAccent))
            .setColorized(true)
            .setOngoing(true)
            .setPriority(NotificationManagerCompat.IMPORTANCE_LOW)
            .setSmallIcon(R.drawable.ic_android_notif)
            .setTicker(contentName)
            .setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
            .setWhen(System.currentTimeMillis())

        return builder.build()
    }

    private fun createNotificationChannel(): String {
        if (isAtLeastO()) {
            val channel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
                )
            channel.lightColor = R.color.colorAccent
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            return channel.id
        }
        return NOTIFICATION_CHANNEL_ID
    }
}

private const val NOTIFICATION_CHANNEL_ID = "Downloads"
private const val NOTIFICATION_CHANNEL_NAME = "Download Notifications"
private const val NOTIFICATION_ID: Int = 97234923
const val DOWNLOAD_BROADCAST = "download_update_broadcast"
const val DOWNLOAD_INTENT_REQUEST_CODE = 139641836
