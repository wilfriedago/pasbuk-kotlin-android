package dev.claucookielabs.pasbuk.common.data.datasource.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbPassbook(
    val formatVersion: Int,
    val passTypeIdentifier: String,
    @PrimaryKey val serialNumber: String,
    val teamIdentifier: String,
    val authenticationToken: String,
    val webServiceURL: String?,
    val organizationName: String,
    val description: String?,
    @Embedded val barcode: DbBarcode?,
    @Embedded  val location: DbLocation?,
    val maxDistance: Int?,
    val relevantDate: String?,
    val updateDate: Long?,
    val backgroundColor: String?,
    val foregroundColor: String?,
    val labelColor: String?,
    val logoText: String?,
    @Embedded val pass: DbPass,
    val logoImage: String?,
    val backgroundImage: String?,
    val stripImage: String?,
    val thumbnailImage: String?,
    val pkpassFile: String
)

data class DbBarcode(
    val barcodeFormat: String,
    val barcodeMessage: String,
    val messageEncoding: String,
    val altText: String? = ""
)

data class DbLocation(
    val latitude: Double,
    val longitude: Double,
    val locationRelevantText: String?
)

data class DbPass(
    val headerFields: List<DbInfoField>,
    val primaryFields: List<DbInfoField>,
    val secondaryFields: List<DbInfoField>,
    val backFields: List<DbInfoField>,
    val auxiliaryFields: List<DbInfoField>,
    val transitType: String,
    val passType: String
)

data class DbInfoField(
    val key: String,
    val value: String,
    val label: String,
    val currencyCode: String? = null,
    val attributedValue: String? = null,
    val changeMessage: String? = null,
    val dataDetectorTypes: List<String>?,
    val textAlignment: String,
    val dateStyle: String,
    val timeStyle: String
)
