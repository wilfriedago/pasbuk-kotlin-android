package dev.claucookielabs.pasbuk.common.data.datasource.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * This class represents group of fields the pass can contain.
 *
 * @property auxiliaryFieldNetworks Additional fields to be displayed on the front of the pass.
 * @property backFieldNetworks Fields that go on the back of the pass.
 * @property headerFieldNetworks Fields to be displayed in the header on the front of the pass.
 * @property primaryFieldNetworks Fields to be displayed prominently on the front of the pass.
 * @property secondaryFieldNetworks Fields to be displayed on the front of the pass.
 * @property transitType Required for boarding passes; otherwise not allowed.
 * Type of transit. Must be one of the following values:
 * PKTransitTypeAir, PKTransitTypeBoat, PKTransitTypeBus, PKTransitTypeGeneric,PKTransitTypeTrain.
 */
@Parcelize
data class NetworkPass(
    val headerFields: List<NetworkInfoField>?,
    val primaryFields: List<NetworkInfoField>?,
    val secondaryFields: List<NetworkInfoField>?,
    val backFields: List<NetworkInfoField>?,
    val auxiliaryFields: List<NetworkInfoField>?,
    val transitType: NetworkTransitType?
) : Parcelable

enum class NetworkTransitType(val typeName: String) {
    @SerializedName("PKTransitTypeAir")
    Air("PKTransitTypeAir"),
    @SerializedName("PKTransitTypeBoat")
    Boat("PKTransitTypeBoat"),
    @SerializedName("PKTransitTypeBus")
    Bus("PKTransitTypeBus"),
    @SerializedName("PKTransitTypeGeneric")
    Generic("PKTransitTypeGeneric"),
    @SerializedName("PKTransitTypeTrain")
    Train("PKTransitTypeTrain")
}
