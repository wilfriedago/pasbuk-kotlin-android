package dev.claucookielabs.pasbuk.common.data.repository

import dev.claucookielabs.pasbuk.common.data.datasource.PassesDatasource
import dev.claucookielabs.pasbuk.common.data.datasource.network.model.*
import dev.claucookielabs.pasbuk.common.domain.model.*

class PassesRepository(private val passesDatasource: PassesDatasource) {

    fun mockPasses(): List<Passbook> {
        return listOfPasses().map {
            it.toDomain()
        }
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

    @SuppressWarnings("LongMethod")
    private fun listOfPasses() = listOf(
        NetworkPassbook(
            formatVersion = 1,
            serialNumber = "12345",
            passTypeIdentifier = "3u02u",
            teamIdentifier = "the team",
            authenticationToken = "wf2398fhqc3fhfhw",
            organizationName = "alsa",
            boardingPass = NetworkPass(
                headerFields = listOf(
                    NetworkInfoField(
                        key = "header",
                        label = "Localizador",
                        value = "1D1VWML"
                    ),
                    NetworkInfoField(
                        key = "platform",
                        label = "anden",
                        value = "30"
                    )
                ),
                primaryFields = listOf(
                    NetworkInfoField(
                        key = "origin",
                        label = "CORDOBA",
                        value = "07-08-16 18:45h   "
                    ),
                    NetworkInfoField(
                        key = "destination",
                        label = "SEVILLA",
                        value = "07-08-16 20:40h   "
                    )
                ),
                secondaryFields = listOf(
                    NetworkInfoField(
                        key = "ticket",
                        label = "Número billete",
                        value = "203-1-999-7883015"
                    ),
                    NetworkInfoField(
                        key = "locator",
                        label = "Localizador",
                        value = "1D1VWML",
                        textAlignment = NetworkTextAlignment.Right
                    )
                ),
                auxiliaryFields = listOf(
                    NetworkInfoField(
                        key = "name",
                        label = "Nombre",
                        value = "PATRICIA LUQUE"
                    ),
                    NetworkInfoField(
                        key = "numBus",
                        label = "N.Bus",
                        value = "1"
                    ),
                    NetworkInfoField(
                        key = "seat",
                        label = "Asiento",
                        value = "13",
                        textAlignment = NetworkTextAlignment.Right
                    )
                ),
                transitType = NetworkTransitType.Bus,
                backFields = listOf(
                    NetworkInfoField(
                        key = "yourReservation",
                        label = "Tu Reserva",
                        value = "Nombre: PATRICIA LUQUE\n" +
                                "Documento: 45745615H\n" +
                                "Origen: CORDOBA\n" +
                                "Destino: SEVILLA(A)\n" +
                                "Fecha Salida: 07-08-16 18:45h\n" +
                                "Fecha Llegada: 07-08-16 20:40h\n" +
                                "Asiento: 13\n" +
                                "Tipo Bus: Normal\n" +
                                "Localizador: 1D1VWML\n" +
                                "Número Billete: 203-1-999-7883015\n" +
                                "Línea: (null)\n" +
                                "Número Bus: 1"
                    )
                )
            ),
            pkpassFile = "/balblabla/path/201911251200.pkpass"
        )
    )

    fun savePassbook(networkPassbook: NetworkPassbook): Boolean {
        return passesDatasource.savePass(networkPassbook)
    }
}

