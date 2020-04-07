package dev.claucookielabs.pasbuk.passdownload.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.common.data.datasource.network.model.NetworkPassbook
import dev.claucookielabs.pasbuk.common.presentation.utils.isAtLeastO
import dev.claucookielabs.pasbuk.passdownload.presentation.ui.PassDownloadActivity
import dev.claucookielabs.pasbuk.passdownload.services.model.IntentScheme
import org.koin.android.ext.android.inject
import java.io.File
import java.io.InputStream
import java.net.URI
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream


class PassDownloadService : IntentService("PassDownloadService") {

    private val intentDataProvider: IntentContentProvider by inject()

    override fun onHandleIntent(intent: Intent?) {
        Log.i("Info", "Download Service onHandleIntent")
        intentDataProvider.parse(intent?.data).let {
            startForeground(NOTIFICATION_ID, createNotification(it.filename))
            var intentData = it
            if (it.isStoredInServer()) {
                Log.i("Info", "Download Service downloading file")
                intentData = intentDataProvider.downloadFile(it.uri.toString())
            }
            val networkPassbook = unzipFile(intentData)
            networkPassbook?.pkpassFile = intentData.uri.path ?: ""
            Log.i("Info", "Download Service downloaded an unzipped file ${networkPassbook?.pkpassFile}")
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("Info", "Download Service onCreate")
    }

    override fun onDestroy() {
        Log.i("Info", "Download Service onDestroy")
        super.onDestroy()
    }

    private fun unzipFile(intentScheme: IntentScheme): NetworkPassbook? {
        if (intentScheme == IntentScheme.NotFound) return null
        Log.i("Info", "Download Service unzipping")
        val downloadedFileInputStream: InputStream =
            contentResolver.openInputStream(intentScheme.uri)!!
        val zipFile = ZipFile(File(URI.create(intentScheme.uri.toString())))
        val passEntry = zipFile.getEntry("pass.json")
        val passInputStream = zipFile.getInputStream(passEntry)
        val passContent =
            intentDataProvider.readStringBuilderFromInputStream(passInputStream).toString()
                .apply {
                    // Trying to correct some format errors like ",}" "},]"
                    replace("\\,\\s*\\}".toRegex(), " }")
                    replace("\\},\\s*\\]".toRegex(), "} ]")
                }
        var passbook = Gson().fromJson(passContent, NetworkPassbook::class.java)
        Log.i("Info", passContent)
        return unzipImages(downloadedFileInputStream, passbook)
    }

    private fun unzipImages(inputStream: InputStream, passbook: NetworkPassbook): NetworkPassbook {
        try {
            val zis = ZipInputStream(inputStream)
            var entry: ZipEntry?
            while (null != zis.nextEntry.also { entry = it }) {
                when (entry!!.name) {
                    "logo.png", "logo@2x.png", "background.png", "thumbnail.png", "strip.png" -> {
                        val imgBytes = intentDataProvider.readBytesFromInputStream(zis)
                        val imgPath = intentDataProvider.createImageFileAndGetPath(
                            this,
                            passbook.serialNumber,
                            entry!!.name,
                            imgBytes
                        )
                        passbook.setImage(entry!!.name, imgPath ?: "")
                    }
                }
            }
            zis.close()
            inputStream.close()
        } catch (e: Exception) {
            Log.e("Error", "Download Service " + e.message)
        }
        Log.i("Info", "Download Service json pass created")
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
