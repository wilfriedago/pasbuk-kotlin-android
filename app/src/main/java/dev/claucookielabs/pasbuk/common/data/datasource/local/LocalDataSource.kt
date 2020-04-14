package dev.claucookielabs.pasbuk.common.data.datasource.local

import dev.claucookielabs.pasbuk.common.data.datasource.PassesDatasource
import dev.claucookielabs.pasbuk.common.data.datasource.local.model.DbPassbook

class LocalDataSource(
    private val passbookDatabase: PassbookDatabase
) : PassesDatasource {

    /**
     * This method saves the pass in the shared preferences.
     *
     * @return False if the pass already exists, True otherwise
     */
    override fun savePass(dbPassbook: DbPassbook): Boolean {
        val passesList = getPasses().toMutableList()
        if (passesList.none { it.serialNumber == dbPassbook.serialNumber }) {
            passbookDatabase.passbookDao().savePass(dbPassbook)
            return true
        }
        return false
    }

    override fun getPasses(): List<DbPassbook> {
        return passbookDatabase.passbookDao().getAllPasses()
    }
}
