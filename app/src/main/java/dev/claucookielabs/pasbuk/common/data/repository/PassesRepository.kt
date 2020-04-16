package dev.claucookielabs.pasbuk.common.data.repository

import dev.claucookielabs.pasbuk.common.data.datasource.PassesDatasource
import dev.claucookielabs.pasbuk.common.data.datasource.network.model.NetworkPassbook
import dev.claucookielabs.pasbuk.common.data.repository.mappers.toDb
import dev.claucookielabs.pasbuk.common.data.repository.mappers.toDomain
import dev.claucookielabs.pasbuk.common.domain.model.Passbook

class PassesRepository(private val passesDatasource: PassesDatasource) {

    fun getPasses(): List<Passbook> {
        return passesDatasource.getPasses().map {
            it.toDomain()
        }
    }

    fun savePassbook(networkPassbook: NetworkPassbook): Boolean {
        return passesDatasource.savePass(networkPassbook.toDb())
    }
}

