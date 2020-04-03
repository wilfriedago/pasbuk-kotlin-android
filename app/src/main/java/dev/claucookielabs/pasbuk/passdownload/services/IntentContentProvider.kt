package dev.claucookielabs.pasbuk.passdownload.services

import android.net.Uri
import android.util.Log
import dev.claucookielabs.pasbuk.passdownload.services.model.IntentScheme
import java.io.*
import java.net.URL

class IntentContentProvider {

    fun parse(data: Uri?): IntentScheme {
        return data?.scheme?.let {
            when (it) {
                "http", "https" -> IntentScheme.Http(data)
                "content" -> IntentScheme.Scheme(data)
                "file" -> IntentScheme.File(data)
                else -> IntentScheme.NotFound
            }
        } ?: IntentScheme.NotFound
    }

    fun downloadFile(url: String): IntentScheme {
        try {
            val u = URL(url)
            val stream = DataInputStream(u.openStream())
            val buffer = ByteArray(500000)
            stream.readFully(buffer)
            stream.close()
            val tempFile = File.createTempFile("pasbuk_", ".pkpass")
            val fos = DataOutputStream(FileOutputStream(tempFile))
            fos.write(buffer)
            fos.flush()
            fos.close()
            return IntentScheme.File(Uri.parse(tempFile.toURI().toString()))
        } catch (e: FileNotFoundException) {
            return IntentScheme.NotFound
        } catch (e: IOException) {
            return IntentScheme.NotFound
        }
    }
}
