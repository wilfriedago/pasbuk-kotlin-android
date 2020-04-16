package dev.claucookielabs.pasbuk.common.data.datasource.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * This class represents all the information contained in a Passbook file.
 *
 * @property formatVersion Version of the file. The value must be 1.
 * @property serialNumber Serial number that uniquely identifies the pass. No two passes with the same
 * pass type identifier may have the same serial number.
 * @property passTypeIdentifier Pass type identifier, as issued by Apple. The value must correspond with
 * your signing certificate.
 * @property teamIdentifier Team identifier of the organization that originated and signed the pass, as
 * issued by Apple.
 * @property authenticationToken The authentication token to use with the web service. The token must be
 * 16 characters or longer.
 * @property webServiceURL The URL of a web service used to download the latest information about the pass.
 * It should comply with the API described in Passbook Web Service Reference.
 * The web service must use the HTTPS protocol; the leading https:// is included in the value of this key.
 * @property organizationName The name of the organization who issued the pass.
 * @property description
 * @property barcode
 * @property locations
 * @property maxDistance
 * @property relevantDate
 * @property updateDate
 * @property backgroundColor
 * @property foregroundColor
 * @property labelColor
 * @property logoText
 * @property boardingPass
 * @property coupon
 * @property eventTicket
 * @property generic
 * @property storeCard
 * @property logoImage
 * @property backgroundImage Image url used for events.
 * @property stripImage Image url used for coupons.
 * @property thumbnailImage Image url used for generic passes.
 * @property pkpassFile Path url to access the pkpass file.
 */
@Parcelize
data class NetworkPassbook(
    val formatVersion: Int,
    val serialNumber: String,
    val passTypeIdentifier: String,
    val teamIdentifier: String,
    val authenticationToken: String,
    val webServiceURL: String? = null,
    val organizationName: String,
    val description: String? = null,
    val barcode: NetworkBarcode? = null,
    val location: NetworkLocation? = null,
    val maxDistance: Int? = null,
    val relevantDate: String? = null,
    val updateDate: Long? = null,
    val backgroundColor: String? = null,
    val foregroundColor: String? = null,
    val labelColor: String? = null,
    val logoText: String? = null,
    val boardingPass: NetworkPass? = null,
    val coupon: NetworkPass? = null,
    val eventTicket: NetworkPass? = null,
    val generic: NetworkPass? = null,
    val storeCard: NetworkPass? = null,
    var logoImage: String? = null,
    var backgroundImage: String? = null,
    var stripImage: String? = null,
    var thumbnailImage: String? = null,
    var pkpassFile: String
) : Parcelable {

    fun setImage(name: String, imgPath: String) {
        when (name) {
            LOGO_FILENAME -> if (logoImage == null) logoImage = imgPath
            LOGO2X_FILENAME -> logoImage = imgPath
            BACKGROUND_FILENAME -> backgroundImage = imgPath
            THUMBNAIL_FILENAME -> thumbnailImage = imgPath
            STRIP_FILENAME -> stripImage = imgPath
        }
    }

    fun containsImageNamed(name: String): Boolean {
        return listOf(
            LOGO_FILENAME,
            LOGO2X_FILENAME,
            BACKGROUND_FILENAME,
            THUMBNAIL_FILENAME,
            STRIP_FILENAME
        ).contains(name)
    }

    fun getPassType(): String {
        return ""
    }
}

private const val LOGO_FILENAME = "logo.png"
private const val LOGO2X_FILENAME = "logo@2x.png"
private const val BACKGROUND_FILENAME = "background.png"
private const val THUMBNAIL_FILENAME = "thumbnail.png"
private const val STRIP_FILENAME = "strip.png"
