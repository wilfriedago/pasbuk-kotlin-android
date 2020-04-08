package dev.claucookielabs.pasbuk.passdownload.services

import android.content.ContentResolver.SCHEME_CONTENT
import android.content.ContentResolver.SCHEME_FILE
import android.content.Context
import android.net.Uri
import dev.claucookielabs.pasbuk.passdownload.services.model.IntentScheme
import java.io.*
import java.net.URL


class IntentDataProvider {

    fun parse(data: Uri?): IntentScheme {
        return data?.scheme?.let {
            when (it) {
                "http", "https" -> IntentScheme.Http(data)
                SCHEME_CONTENT -> IntentScheme.Content(data)
                SCHEME_FILE -> IntentScheme.File(data)
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
            DataOutputStream(FileOutputStream(tempFile)).use {
                it.write(buffer)
                it.flush()
            }
            IntentScheme.File(Uri.parse(tempFile.toURI().toString()))
        } catch (e: FileNotFoundException) {
            IntentScheme.NotFound
        } catch (e: IOException) {
            IntentScheme.NotFound
        }
    }

    fun readBytesFromInputStream(stream: InputStream): ByteArray? {
        var fileBytes: ByteArray? = null
        ByteArrayOutputStream().use {
            val buffer = ByteArray(1024)
            var count: Int
            while (stream.read(buffer).also { count = it } != -1) {
                it.write(buffer, 0, count)
            }
            fileBytes = it.toByteArray()
        }
        return fileBytes
    }

    fun readPassContentFromInputStream(stream: InputStream): String {
        val stringBuilder = StringBuilder()
        var read: Int
        val buffer = ByteArray(1024)
        while (stream.read(buffer, 0, 1024).also { read = it } >= 0) {
            stringBuilder.append(String(buffer, 0, read))
        }
        return stringBuilder.toString().apply {
            // Trying to correct some format errors like ",}" "},]"
            replace("\\,\\s*\\}".toRegex(), " }")
            replace("\\},\\s*\\]".toRegex(), "} ]")
        }
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
            BufferedOutputStream(FileOutputStream(file)).use {
                it.write(imageBytes)
                it.flush()
            }
            return file.absolutePath
        }
        return null
    }

    fun retrieveFile(context: Context, intentScheme: IntentScheme): File {
        val tempFile = File(context.cacheDir, "tempfile.pkpass")
        context.contentResolver.openInputStream(intentScheme.uri)?.use { input ->
            FileOutputStream(tempFile).use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
        return tempFile
    }
}
