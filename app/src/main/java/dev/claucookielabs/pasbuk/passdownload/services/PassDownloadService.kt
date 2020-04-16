package dev.claucookielabs.pasbuk.passdownload.services

import android.app.*
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.common.data.datasource.network.model.NetworkPassbook
import dev.claucookielabs.pasbuk.common.data.repository.PassesRepository
import dev.claucookielabs.pasbuk.common.presentation.utils.isAtLeastO
import dev.claucookielabs.pasbuk.passdownload.presentation.ui.PassDownloadActivity
import dev.claucookielabs.pasbuk.passdownload.services.model.IntentScheme
import org.koin.android.ext.android.inject
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream


class PassDownloadService : IntentService("PassDownloadService") {

    private val intentDataHelper: IntentDataHelper by inject()
    private val passesRepository: PassesRepository by inject()

    override fun onHandleIntent(intent: Intent?) {
        Log.i("Info", "Download Service onHandleIntent")
        intentDataHelper.parse(intent?.data).let {
            val resultReceiver: ResultReceiver = intent!!.getParcelableExtra("receiver")
            // We tell the system this service is important
            startForeground(NOTIFICATION_ID, createNotification(it.filename))
            val intentData =
                if (it.isStoredInServer()) intentDataHelper.downloadFile(it.uri.toString())
                else it
            unzipFile(intentData)?.apply {
                pkpassFile = intentData.uri.path ?: ""
                // If false, the pass already exists
                passesRepository.savePassbook(this)
            }
            resultReceiver.send(RESULT_OK, Bundle())
        }
    }

    private fun unzipFile(intentScheme: IntentScheme): NetworkPassbook? {
        if (intentScheme == IntentScheme.NotFound) return null
        val file = intentDataHelper.retrieveFile(this, intentScheme)
        try {
            val zipFile = ZipFile(file)
            val passEntry = zipFile.getEntry("pass.json")
            zipFile.getInputStream(passEntry).use {
                var passbook: NetworkPassbook?
                val pass = intentDataHelper.readPassContentFromInputStream(it)
                passbook = Gson().fromJson(pass, NetworkPassbook::class.java)
                passbook = unzipAndSetImages(intentScheme, passbook)
                Log.i("Info", pass)
                return passbook
            }
        } catch (exception: ZipException) {
            Log.e(javaClass.simpleName, file.name + ": The file is not a zipped file, " + exception.message)
            return null
        }
    }

    private fun unzipAndSetImages(
        intentScheme: IntentScheme,
        passbook: NetworkPassbook
    ): NetworkPassbook {
        contentResolver.openInputStream(intentScheme.uri)?.use { inputStream ->
            ZipInputStream(inputStream).use { zis ->
                var entry: ZipEntry?
                while (null != zis.nextEntry.also { entry = it }) {
                    if (passbook.containsImageNamed(entry!!.name)) {
                        val imgBytes = intentDataHelper.readBytesFromInputStream(zis)
                        val imgPath = intentDataHelper.createImageFileAndGetPath(
                            this,
                            passbook.serialNumber,
                            entry!!.name,
                            imgBytes
                        )
                        passbook.setImage(entry!!.name, imgPath ?: "")
                    }
                }
            }
        }
        return passbook
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
            val channel = NotificationChannel(
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
