package dev.claucookielabs.pasbuk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * This class represents the information about a specific location.
 *
 * @property latitude The latitude
 * @property longitude The longitude
 * @property relevantText Extra information about the location: e.g. the name of the venue
 */
@Parcelize
data class Location(
    val latitude: Double,
    val longitude: Double,
    val relevantText: String? = null
) : Parcelable
