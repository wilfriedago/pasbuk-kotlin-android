package dev.claucookielabs.pasbuk.common.data.repository.mappers

import dev.claucookielabs.pasbuk.common.data.datasource.local.model.*
import dev.claucookielabs.pasbuk.common.data.datasource.network.model.*

fun NetworkPassbook.toDb(): DbPassbook {
    return DbPassbook(
        formatVersion = this.formatVersion,
        serialNumber = this.serialNumber,
        passTypeIdentifier = this.passTypeIdentifier,
        teamIdentifier = this.teamIdentifier,
        authenticationToken = this.authenticationToken,
        webServiceURL = this.webServiceURL,
        organizationName = this.organizationName,
        description = this.description,
        barcode = this.barcode.toDb(),
        location = this.location.toDb(),
        maxDistance = this.maxDistance,
        relevantDate = this.relevantDate,
        updateDate = this.updateDate,
        backgroundColor = this.backgroundColor,
        foregroundColor = this.foregroundColor,
        labelColor = this.labelColor,
        logoText = this.logoText,
        pass = this.boardingPass.toDb(),
        passType = this.getPassType(),
        // Model all type of passes in one for Domain layer
        logoImage = this.logoImage,
        backgroundImage = this.backgroundImage,
        stripImage = this.stripImage,
        thumbnailImage = this.thumbnailImage,
        pkpassFile = this.pkpassFile
    )
}


fun NetworkPass?.toDb(): DbPass {
    return DbPass(
        headerFields = this?.headerFields.toDb(),
        primaryFields = this?.primaryFields.toDb(),
        secondaryFields = this?.secondaryFields.toDb(),
        backFields = this?.backFields.toDb(),
        auxiliaryFields = this?.auxiliaryFields.toDb(),
        transitType = this?.transitType?.typeName ?: ""
    )
}

fun NetworkLocation?.toDb(): DbLocation? {
    return this?.run {
        DbLocation(
            latitude = this.latitude,
            longitude = this.longitude,
            locationRelevantText = this.relevantText
        )
    }
}

fun NetworkBarcode?.toDb(): DbBarcode? {
    return this?.run {
        DbBarcode(
            barcodeFormat = this.format,
            barcodeMessage = this.message,
            messageEncoding = this.messageEncoding,
            altText = this.altText
        )
    }
}


fun List<NetworkInfoField>?.toDb(): ArrayList<DbInfoField> {
    val fields = ArrayList<DbInfoField>()
    if (this?.isEmpty() == false) {
        this.forEach {
            fields.add(
                DbInfoField(
                    key = it.key,
                    value = it.value,
                    label = it.label ?: "",
                    currencyCode = it.currencyCode,
                    attributedValue = it.attributedValue,
                    changeMessage = it.changeMessage,
                    dataDetectorTypes = it.dataDetectorTypes ?: emptyList(),
                    textAlignment = it.textAlignment?.alignmentName
                        ?: NetworkTextAlignment.Natural.alignmentName,
                    dateStyle = it.dateStyle?.styleName ?: NetworkDateStyle.NoStyle.styleName,
                    timeStyle = it.timeStyle?.styleName ?: NetworkDateStyle.NoStyle.styleName
                )
            )
        }
    }
    return fields
}
