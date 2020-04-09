package dev.claucookielabs.pasbuk.common.data.repository

import dev.claucookielabs.pasbuk.common.data.datasource.PassesDatasource
import dev.claucookielabs.pasbuk.common.data.datasource.network.model.*
import dev.claucookielabs.pasbuk.common.domain.model.*

class PassesRepository(private val passesDatasource: PassesDatasource) {

    fun getPasses(): List<Passbook> {
        return passesDatasource.getPasses().map {
            it.toDomain()
        }
    }
    
    fun savePassbook(networkPassbook: NetworkPassbook): Boolean {
        return passesDatasource.savePass(networkPassbook)
    }

    private fun NetworkPassbook.toDomain(): Passbook {
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
            boardingPass = this.boardingPass.toDomain(),
            // Model all type of passes in one for Domain layer
            logoImage = this.logoImage,
            backgroundImage = this.backgroundImage,
            stripImage = this.stripImage,
            thumbnailImage = this.thumbnailImage,
            pkpassFile = this.pkpassFile
        )
    }


    private fun NetworkPass?.toDomain(): Pass {
        return Pass(
            headerFields = this?.headerFields.toDomain(),
            primaryFields = this?.primaryFields.toDomain(),
            secondaryFields = this?.secondaryFields.toDomain(),
            backFields = this?.backFields.toDomain(),
            auxiliaryFields = this?.auxiliaryFields.toDomain(),
            transitType = this?.transitType.toDomain()
        )
    }

    private fun NetworkLocation?.toDomain(): Location {
        return this?.run {
            Location(
                latitude = this.latitude,
                longitude = this.longitude,
                relevantText = this.relevantText
            )
        } ?: EmptyLocation()
    }

    private fun NetworkBarcode?.toDomain(): Barcode {
        return this?.run {
            Barcode(
                format = this.format,
                message = this.message,
                messageEncoding = this.messageEncoding,
                altText = this.altText
            )
        } ?: Barcode.EMPTY
    }


    private fun List<NetworkInfoField>?.toDomain(): List<InfoField> {
        return this?.let { infoFields ->
            return infoFields.map {
                InfoField(
                    key = it.key,
                    value = it.value,
                    label = it.label,
                    currencyCode = it.currencyCode,
                    attributedValue = it.attributedValue,
                    changeMessage = it.changeMessage,
                    dataDectectorTypes = it.dataDectectorTypes,
                    textAlignment = it.textAlignment.toDomain(),
                    dateStyle = it.dateStyle.toDomain(),
                    timeStyle = it.timeStyle
                )
            }
        } ?: listOf(InfoField.EMPTY)
    }

    private fun NetworkTextAlignment?.toDomain(): TextAlignment {
        return when (this) {
            NetworkTextAlignment.Left -> TextAlignment.Left
            NetworkTextAlignment.Center -> TextAlignment.Center
            NetworkTextAlignment.Right -> TextAlignment.Right
            else -> TextAlignment.Natural
        }
    }

    private fun NetworkDateStyle?.toDomain(): DateStyle {
        return when (this) {
            NetworkDateStyle.ShortStyle -> DateStyle.ShortStyle
            NetworkDateStyle.MediumStyle -> DateStyle.MediumStyle
            NetworkDateStyle.LongStyle -> DateStyle.LongStyle
            NetworkDateStyle.FullStyle -> DateStyle.FullStyle
            else -> DateStyle.NoStyle
        }
    }

    private fun NetworkTransitType?.toDomain(): TransitType {
        return when (this) {
            NetworkTransitType.Air -> TransitType.Air
            NetworkTransitType.Boat -> TransitType.Boat
            NetworkTransitType.Bus -> TransitType.Bus
            NetworkTransitType.Train -> TransitType.Train
            else -> TransitType.Generic
        }
    }
}

