package dev.claucookielabs.pasbuk.common.data.datasource

import dev.claucookielabs.pasbuk.common.data.datasource.network.model.NetworkPassbook

interface PassesDatasource {

    fun savePass(networkPassbook: NetworkPassbook) : Boolean
    fun getPasses(): List<NetworkPassbook>
}
