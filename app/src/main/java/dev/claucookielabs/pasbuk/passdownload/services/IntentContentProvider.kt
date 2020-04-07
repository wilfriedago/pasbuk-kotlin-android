package dev.claucookielabs.pasbuk.passdownload.services

import android.content.Context
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
        return try {
            val u = URL(url)
            val stream = DataInputStream(u.openStream())
            val buffer = readBytesFromInputStream(stream)
            val tempFile = File.createTempFile("pasbuk_", ".pkpass")
            val fos = DataOutputStream(FileOutputStream(tempFile))
            fos.write(buffer)
            fos.flush()
            fos.close()
            IntentScheme.File(Uri.parse(tempFile.toURI().toString()))
        } catch (e: FileNotFoundException) {
            IntentScheme.NotFound
        } catch (e: IOException) {
            IntentScheme.NotFound
        }
    }

    fun readBytesFromInputStream(stream: InputStream): ByteArray? {
        var fileBytes: ByteArray?
        val baos = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var count: Int
        try {
            while (stream.read(buffer).also { count = it } != -1) {
                baos.write(buffer, 0, count)
            }
        } catch (e: IOException) {
            Log.e("Error", "Download Service " + e.message)
        }
        fileBytes = baos.toByteArray()
        return fileBytes
    }

    fun readStringBuilderFromInputStream(stream: InputStream): StringBuilder {
        val stringBuilder = StringBuilder()
        var read = 0
        val buffer = ByteArray(1024)
        while (stream.read(buffer, 0, 1024).also { read = it } >= 0) {
            stringBuilder.append(String(buffer, 0, read))
        }
        return stringBuilder
    }

    fun createImageFileAndGetPath(
        context: Context,
        imagesDir: String,
        imageName: String,
        imageBytes: ByteArray?
    ): String? {
        val folder = File("${context.filesDir.absolutePath}/${imagesDir}")
        if (folder.exists() || folder.mkdirs()) {
            val file = File(folder.absolutePath, "/${imageName}")
            val bos = BufferedOutputStream(FileOutputStream(file))
            bos.write(imageBytes)
            bos.flush()
            bos.close()
            return file.absolutePath
        }
        return null
    }
}
