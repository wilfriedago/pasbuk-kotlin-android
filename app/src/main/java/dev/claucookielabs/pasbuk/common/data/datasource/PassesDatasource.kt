package dev.claucookielabs.pasbuk.common.data.datasource

import dev.claucookielabs.pasbuk.common.data.datasource.local.model.DbPassbook

interface PassesDatasource {

    fun savePass(networkPassbook: DbPassbook): Boolean
    fun getPasses(): List<DbPassbook>
}
