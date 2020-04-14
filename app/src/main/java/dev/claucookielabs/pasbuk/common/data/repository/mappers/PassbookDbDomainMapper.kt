package dev.claucookielabs.pasbuk.common.data.repository.mappers

import dev.claucookielabs.pasbuk.common.data.datasource.local.model.*
import dev.claucookielabs.pasbuk.common.domain.model.*

fun DbPassbook.toDomain(): Passbook {
    return Passbook(
        formatVersion = this.formatVersion,
        serialNumber = this.serialNumber,
        passTypeIdentifier = this.passTypeIdentifier,
        teamIdentifier = this.teamIdentifier,
        authenticationToken = this.authenticationToken,
        webServiceURL = this.webServiceURL,
        organizationName = this.organizationName,
        description = this.description,
        barcode = this.barcode.toDomain(),
        location = this.location.toDomain(),
        maxDistance = this.maxDistance,
        relevantDate = this.relevantDate,
        updateDate = this.updateDate,
        backgroundColor = this.backgroundColor,
        foregroundColor = this.foregroundColor,
        labelColor = this.labelColor,
        logoText = this.logoText,
        boardingPass = this.pass.toDomain(),
        // Model all type of passes in one for Domain layer
        logoImage = this.logoImage,
        backgroundImage = this.backgroundImage,
        stripImage = this.stripImage,
        thumbnailImage = this.thumbnailImage,
        pkpassFile = this.pkpassFile
    )
}


fun DbPass?.toDomain(): Pass {
    return Pass(
        headerFields = this?.headerFields.toDomain(),
        primaryFields = this?.primaryFields.toDomain(),
        secondaryFields = this?.secondaryFields.toDomain(),
        backFields = this?.backFields.toDomain(),
        auxiliaryFields = this?.auxiliaryFields.toDomain(),
        transitType = TransitType.fromName(this?.transitType)
    )
}

fun DbLocation?.toDomain(): Location {
    return this?.run {
        Location(
            latitude = this.latitude,
            longitude = this.longitude,
            relevantText = this.locationRelevantText
        )
    } ?: EmptyLocation()
}

fun DbBarcode?.toDomain(): Barcode {
    return this?.run {
        Barcode(
            format = this.barcodeFormat,
            message = this.barcodeMessage,
            messageEncoding = this.messageEncoding,
            altText = this.altText
        )
    } ?: Barcode.EMPTY
}


fun List<DbInfoField>?.toDomain(): List<InfoField> {
    return this?.let { infoFields ->
        return infoFields.map {
            InfoField(
                key = it.key,
                value = it.value,
                label = it.label,
                currencyCode = it.currencyCode,
                attributedValue = it.attributedValue,
                changeMessage = it.changeMessage,
                dataDetectorTypes = it.dataDetectorTypes ?: emptyList(),
                textAlignment = TextAlignment.fromName(it.textAlignment),
                dateStyle = DateStyle.fromName(it.dateStyle),
                timeStyle = DateStyle.fromName(it.dateStyle)
            )
        }
    } ?: listOf(InfoField.EMPTY)
}
