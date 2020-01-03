package dev.claucookielabs.pasbuk.model

class PassesRepository {

    @SuppressWarnings("LongMethod")
    fun mockPasses() = listOf(
        Passbook(
            formatVersion = 1,
            serialNumber = "12345",
            passTypeIdentifier = "3u02u",
            teamIdentifier = "the team",
            authenticationToken = "wf2398fhqc3fhfhw",
            organizationName = "alsa",
            boardingPass = PassInfo(
                headerFields = listOf(
                    InfoField(
                        key = "header",
                        label = "Localizador",
                        value = "1D1VWML"
                    ),
                    InfoField(
                        key = "platform",
                        label = "anden",
                        value = "30"
                    )
                ),
                primaryFields = listOf(
                    InfoField(
                        key = "origin",
                        label = "CORDOBA",
                        value = "07-08-16 18:45h   "
                    ),
                    InfoField(
                        key = "destination",
                        label = "SEVILLA",
                        value = "07-08-16 20:40h   "
                    )
                ),
                secondaryFields = listOf(
                    InfoField(
                        key = "ticket",
                        label = "Número billete",
                        value = "203-1-999-7883015"
                    ),
                    InfoField(
                        key = "locator",
                        label = "Localizador",
                        value = "1D1VWML",
                        textAlignment = TextAlignment.Right
                    )
                ),
                auxiliaryFields = listOf(
                    InfoField(
                        key = "name",
                        label = "Nombre",
                        value = "PATRICIA LUQUE"
                    ),
                    InfoField(
                        key = "numBus",
                        label = "N.Bus",
                        value = "1"
                    ),
                    InfoField(
                        key = "seat",
                        label = "Asiento",
                        value = "13",
                        textAlignment = TextAlignment.Right
                    )
                ),
                transitType = TransitType.Bus,
                backFields = listOf(
                    InfoField(
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
}
