package dev.claucookielabs.pasbuk.passdownload.services

import android.content.ContentResolver.SCHEME_CONTENT
import android.content.ContentResolver.SCHEME_FILE
import android.content.Context
import android.net.Uri
import dev.claucookielabs.pasbuk.passdownload.services.model.IntentScheme
import java.io.*
import java.net.URL


class IntentDataHelper {

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
        ByteArrayOutputStream().use { outpuStream ->
            val buffer = ByteArray(BUFFER_SIZE)
            var count: Int
            while (stream.read(buffer).also { count = it } != END_OF_STREAM) {
                outpuStream.write(buffer, ZERO_POS, count)
            }
            fileBytes = outpuStream.toByteArray()
        }
        return fileBytes
    }

    fun readPassContentFromInputStream(stream: InputStream): String {
        val stringBuilder = StringBuilder()
        var read: Int
        val buffer = ByteArray(BUFFER_SIZE)
        while (stream.read(buffer, ZERO_POS, BUFFER_SIZE).also { read = it } >= ZERO_POS) {
            stringBuilder.append(String(buffer, ZERO_POS, read))
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
                val buffer = ByteArray(BLOCKS * BUFFER_SIZE)
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < ZERO_POS) break
                    output.write(buffer, ZERO_POS, byteCount)
                }
                output.flush()
            }
        }
        return tempFile
    }
}

private const val BUFFER_SIZE = 1024
private const val ZERO_POS = 0
private const val BLOCKS = 4
private const val END_OF_STREAM = -1
