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
import org.json.JSONObject
import org.koin.android.ext.android.inject
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.zip.ZipEntry
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
            unzipFile(intentData)
        }
    }

    private fun unzipFile(intentScheme: IntentScheme) {
        Log.i("Info", "Download Service unzip")
        if (intentScheme == IntentScheme.NotFound) return
        Log.i("Info", "Download Service unzipping")
        val attachment: InputStream = contentResolver.openInputStream(intentScheme.uri)!!
        val json = unzipInputStream(this, attachment)
        Log.i("Info", json.toString())
    }

    private fun unzipInputStream(
        context: Context,
        inputStream: InputStream
    ): JSONObject? {
        var pass: JSONObject? = JSONObject()
        var logoBytes: ByteArray? = null
        var backgroundBytes: ByteArray? = null
        var stripBytes: ByteArray? = null
        var thumbnailBytes: ByteArray? = null
        try {
            val zis = ZipInputStream(inputStream)
            var entry: ZipEntry
            // Read folder files
            while (zis.nextEntry.also { entry = it } != null) {
                val filename = entry.name
                if (filename == "pass.json") {
                    val s = StringBuilder()
                    var read = 0
                    val buffer = ByteArray(1024)
                    while (zis.read(buffer, 0, 1024).also { read = it } >= 0) s.append(
                        String(
                            buffer,
                            0,
                            read
                        )
                    )
                    var passInfo = s.toString()
                    // Trying to correct some format errors like ",}" "},]"
                    passInfo = passInfo.replace("\\,\\s*\\}".toRegex(), " }")
                    passInfo = passInfo.replace("\\},\\s*\\]".toRegex(), "} ]")
                    pass = JSONObject(passInfo)
                } else if (logoBytes == null && filename == "logo.png") { // Get logo 1x just if logo bytes is not fetted yet, other wise, take 2x logo
                    logoBytes = getBytesFromFileInsizeZip(zis, filename)
                } else if (filename == "logo@2x.png") {
                    logoBytes = getBytesFromFileInsizeZip(zis, filename)
                } else if (filename == "background.png") {
                    backgroundBytes = getBytesFromFileInsizeZip(zis, filename)
                } else if (filename == "thumbnail.png") {
                    thumbnailBytes = getBytesFromFileInsizeZip(zis, filename)
                } else if (filename == "strip.png") {
                    stripBytes = getBytesFromFileInsizeZip(zis, filename)
                }
            }
            inputStream.close()
            if (pass != null) {
                saveImagesToInternalStorage(
                    context,
                    pass,
                    logoBytes,
                    backgroundBytes,
                    stripBytes,
                    thumbnailBytes
                )
            }

        } catch (e: Exception) {
            Log.e("Error", "Download Service " + e.message)
        }
        Log.i("Info", "Download Service json pass created")
        return pass
    }

    private fun getBytesFromFileInsizeZip(
        zis: ZipInputStream,
        filename: String
    ): ByteArray? {
        if (filename.isNotEmpty()) {
            return intentDataProvider.readInputStream(zis)
        }
        return null
    }

    private fun saveImagesToInternalStorage(
        context: Context?,
        pass: JSONObject,
        logoBytes: ByteArray?,
        backgroundBytes: ByteArray?,
        stripBytes: ByteArray?,
        thumbnailBytes: ByteArray?
    ): JSONObject? {
        var pass: JSONObject? = pass
        // For later
        return pass
    }

    private fun createImageFileAndGetPath(
        imagesDir: String,
        imageName: String,
        imageBytes: ByteArray?
    ): String? {
        val storage = File(filesDir.absolutePath + imagesDir + imageName)
        if (storage.mkdirs()) {
            val bos = BufferedOutputStream(FileOutputStream(storage))
            bos.write(imageBytes)
            bos.flush()
            bos.close()
        }
        return storage.absolutePath
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
