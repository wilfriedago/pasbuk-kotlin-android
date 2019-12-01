package dev.claucookielabs.pasbuk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * This class represent a field of information relevant to the pass.
 *
 * @property key The key must be unique within the scope of the entire pass. For example, 'departure-gate'.
 * @property value Value of the field. For example, 42.
 * @property label Label text for the field.
 * @property currencyCode Currency code for stores prices.
 * @property attributedValue
 * @property changeMessage Format string for the alert text that is displayed when the pass is updated.
 * The format string must contain the escape %s, which is replaced with the field’s new value.
 * For example, Gate changed to %s.
 * @property dataDectectorTypes
 * @property textAlignment "Alignment for the field’s contents. Must be one of the following values:
 * PKTextAlignmentLeft PKTextAlignmentCenter PKTextAlignmentRight PKTextAlignmentNatural.
 * The default value is natural alignment, which aligns the text appropriately based on its script direction.
 * @property dateStyle The format for the date: NSDateFormatterNoStyle, NSDateFormatterShortStyle,
 * NSDateFormatterMediumStyle, NSDateFormatterLongStyle, NSDateFormatterFullStyle
 * @property timeStyle The format for the time.
 */
@Parcelize
data class InfoField(
    val key: String,
    val value: String,
    val label: String,
    val currencyCode: String? = null,
    val attributedValue: String? = null,
    val changeMessage: String? = null,
    val dataDectectorTypes: List<String>? = emptyList(),
    val textAlignment: TextAlignment = TextAlignment.Natural,
    val dateStyle: DateStyle = DateStyle.ShortStyle,
    val timeStyle: String? = null
) : Parcelable

enum class TextAlignment(val alignmentName: String) {
    Left("PKTextAlignmentLeft"),
    Center("PKTextAlignmentCenter"),
    Right("PKTextAlignmentRight"),
    Natural("PKTextAlignmentNatural")
}

enum class DateStyle(styleName: String) {
    NoStyle("NSDateFormatterNoStyle"),
    ShortStyle("NSDateFormatterShortStyle"),
    MediumStyle("NSDateFormatterMediumStyle"),
    LongStyle("NSDateFormatterLongStyle"),
    FullStyle("NSDateFormatterFullStyle")
}
