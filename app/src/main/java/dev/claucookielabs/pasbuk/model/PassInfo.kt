package dev.claucookielabs.pasbuk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * This class represents group of fields the pass can contain.
 *
 * @property auxiliaryFields Additional fields to be displayed on the front of the pass.
 * @property backFields Fields that go on the back of the pass.
 * @property headerFields Fields to be displayed in the header on the front of the pass.
 * @property primaryFields Fields to be displayed prominently on the front of the pass.
 * @property secondaryFields Fields to be displayed on the front of the pass.
 * @property transitType Required for boarding passes; otherwise not allowed.
 * Type of transit. Must be one of the following values:
 * PKTransitTypeAir, PKTransitTypeBoat, PKTransitTypeBus, PKTransitTypeGeneric,PKTransitTypeTrain.
 */
@Parcelize
data class PassInfo(
    val headerFields: List<InfoField>,
    val primaryFields: List<InfoField>,
    val secondaryFields: List<InfoField>,
    val backFields: List<InfoField>,
    val auxiliaryFields: List<InfoField>,
    val transitType: TransitType
) : Parcelable

enum class TransitType(typeName: String) {
    Air("PKTransitTypeAir"),
    Boat("PKTransitTypeBoat"),
    Bus("PKTransitTypeBus"),
    Generic("PKTransitTypeGeneric"),
    Train("PKTransitTypeTrain")
}
