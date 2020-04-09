package dev.claucookielabs.pasbuk.passdownload.presentation.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import androidx.appcompat.app.AppCompatActivity
import dev.claucookielabs.pasbuk.R
import dev.claucookielabs.pasbuk.passdownload.services.PassDownloadService
import dev.claucookielabs.pasbuk.passlist.presentation.ui.PassListActivity

class PassDownloadActivity : AppCompatActivity() {

    private val resultReceiver = object : ResultReceiver(Handler()) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            super.onReceiveResult(resultCode, resultData)
            startActivity(
                Intent(this@PassDownloadActivity, PassListActivity::class.java).apply {
                    flags = FLAG_ACTIVITY_CLEAR_TOP
                }
            )
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_download)

        startService(Intent(this, PassDownloadService::class.java).apply {
            data = intent.data
            putExtra("receiver", resultReceiver)
            flags = FLAG_GRANT_READ_URI_PERMISSION
        })
    }
}

