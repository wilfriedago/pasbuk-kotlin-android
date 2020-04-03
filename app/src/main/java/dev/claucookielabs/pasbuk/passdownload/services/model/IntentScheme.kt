package dev.claucookielabs.pasbuk.passdownload.services.model

import android.net.Uri
import android.util.Patterns

/**
 * Examples:
 * https://www.dropbox.com/s/xv91k5bb2vvqh7l/easyjet.pkpass?dl=0
 * content://com.google.android.gm.sapi/claudiathebest15@gmail.com/message_attachment_external/%23thread-f%3A1662873246511674255/%23msg-f%3A1662873246511674255/0.1?account_type=com.google&mimeType=application%2Fvnd-com.apple.pkpass&rendition=1
 */
sealed class IntentScheme(open val uri: Uri) {

    val filename: String
        get() = uri.lastPathSegment ?: ""

    open fun isStoredInServer() = false


    data class Http(override val uri: Uri) : IntentScheme(uri) {
        override fun isStoredInServer(): Boolean = Patterns.WEB_URL.matcher(uri.toString()).matches()
    }

    data class Scheme(override val uri: Uri) : IntentScheme(uri)

    data class File(override val uri: Uri) : IntentScheme(uri)

    object NotFound : IntentScheme(Uri.EMPTY)
}
