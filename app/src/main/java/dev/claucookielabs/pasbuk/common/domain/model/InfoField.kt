package dev.claucookielabs.pasbuk.common.domain.model

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
 * @property dataDetectorTypes
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
    val currencyCode: String?,
    val attributedValue: String?,
    val changeMessage: String?,
    val dataDetectorTypes: List<String>,
    val textAlignment: TextAlignment,
    val dateStyle: DateStyle,
    val timeStyle: DateStyle
) : Parcelable {

    companion object {
        val EMPTY = InfoField(
            key = "",
            value = "",
            label = "",
            currencyCode = "",
            attributedValue = "",
            changeMessage = "",
            dataDetectorTypes = emptyList(),
            textAlignment = TextAlignment.Natural,
            dateStyle = DateStyle.ShortStyle,
            timeStyle = DateStyle.ShortStyle

        )
    }
}

enum class TextAlignment(val alignmentName: String) {
    Left("PKTextAlignmentLeft"),
    Center("PKTextAlignmentCenter"),
    Right("PKTextAlignmentRight"),
    Natural("PKTextAlignmentNatural");

    companion object {
        fun fromName(name: String?): TextAlignment =
            values().firstOrNull { it.alignmentName == name } ?: Natural
    }
}

enum class DateStyle(val styleName: String) {
    NoStyle("NSDateFormatterNoStyle"),
    ShortStyle("NSDateFormatterShortStyle"),
    MediumStyle("NSDateFormatterMediumStyle"),
    LongStyle("NSDateFormatterLongStyle"),
    FullStyle("NSDateFormatterFullStyle");

    companion object {
        fun fromName(name: String?): DateStyle =
            values().firstOrNull { it.styleName == name } ?: ShortStyle
    }
}
