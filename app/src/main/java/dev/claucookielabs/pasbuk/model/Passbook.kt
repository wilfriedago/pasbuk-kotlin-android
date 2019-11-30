package dev.claucookielabs.pasbuk.model

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
 * @property event
 * @property generic
 * @property storeCard
 * @property logoImage
 * @property backgroundImage Image url used for events.
 * @property stripImage Image url used for coupons.
 * @property thumbnailImage Image url used for generic passes.
 * @property pkpassFile Path url to access the pkpass file.
 */
data class Passbook(
    val formatVersion: Int,
    val serialNumber: String,
    val passTypeIdentifier: String,
    val teamIdentifier: String,
    val authenticationToken: String,
    val webServiceURL: String? = null,
    val organizationName: String,
    val description: String? = null,
    val barcode: Barcode? = null,
    val locations: Location? = null,
    val maxDistance: Int? = null,
    val relevantDate: String? = null,
    val updateDate: Long? = null,
    val backgroundColor: String? = null,
    val foregroundColor: String? = null,
    val labelColor: String? = null,
    val logoText: String? = null,
    val boardingPass: PassInfo? = null,
    val coupon: PassInfo? = null,
    val event: PassInfo? = null,
    val generic: PassInfo? = null,
    val storeCard: PassInfo? = null,
    val logoImage: String? = null,
    val backgroundImage: String? = null,
    val stripImage: String? = null,
    val thumbnailImage: String? = null,
    val pkpassFile: String
) {
    val headers: List<InfoField>
        // Since we are hardcoding the passes for now, we know it is a boarding pass
        get() = boardingPass!!.headerFields
}
