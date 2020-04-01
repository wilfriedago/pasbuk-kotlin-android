package dev.claucookielabs.pasbuk.common.data.datasource.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * This class represents the information about the barcode
 *
 * @property format Barcode format. Must be one of the following values:
 * PKBarcodeFormatQR, PKBarcodeFormatPDF417, PKBarcodeFormatAztec.
 * @property message Message or payload to be displayed as a barcode.
 * @property messageEncoding The name of a supported
 * <a href="../lang/package-summary.html#charenc">character encoding</a>.
 * @property altText If known, text displayed near the barcode. For example,
 * a human-readable version of the barcode data in case the barcode doesn’t scan.
 */
@Parcelize
data class NetworkBarcode(
    val format: String,
    val message: String,
    val messageEncoding: String,
    val altText: String? = ""
) : Parcelable
