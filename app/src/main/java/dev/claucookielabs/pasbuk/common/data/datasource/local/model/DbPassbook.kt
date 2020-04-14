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
    val webServiceURL: String? = null,
    val organizationName: String,
    val description: String? = null,
    @Embedded val barcode: DbBarcode? = null,
    @Embedded  val location: DbLocation? = null,
    val maxDistance: Int? = null,
    val relevantDate: String? = null,
    val updateDate: Long? = null,
    val backgroundColor: String? = null,
    val foregroundColor: String? = null,
    val labelColor: String? = null,
    val logoText: String? = null,
    @Embedded val pass: DbPass,
    val passType: String, // Move this inside DbPass
    val logoImage: String? = null,
    val backgroundImage: String? = null,
    val stripImage: String? = null,
    val thumbnailImage: String? = null,
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
    val locationRelevantText: String? = null
)

data class DbPass(
    val headerFields: List<DbInfoField>,
    val primaryFields: List<DbInfoField>,
    val secondaryFields: List<DbInfoField>,
    val backFields: List<DbInfoField>,
    val auxiliaryFields: List<DbInfoField>,
    val transitType: String
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
