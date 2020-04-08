package dev.claucookielabs.pasbuk.passdownload.presentation.ui

import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.passdownload.services.PassDownloadService

class PassDownloadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_download)

        startService(Intent(this, PassDownloadService::class.java).apply {
            data = intent.data
            flags = FLAG_GRANT_READ_URI_PERMISSION
        })
    }
}

