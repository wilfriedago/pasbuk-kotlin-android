package dev.claucookielabs.pasbuk.common.data.datasource.local

import android.content.SharedPreferences
import com.google.gson.Gson
import dev.claucookielabs.pasbuk.common.data.datasource.PassesDatasource
import dev.claucookielabs.pasbuk.common.data.datasource.network.model.NetworkPassbook

class SessionPrefsDataSource(
    private val sharedPrefs: SharedPreferences, private val gson: Gson
) : PassesDatasource {

    /**
     * This method saves the pass in the shared preferences.
     *
     * @return False if the pass already exists, True otherwise
     */
    override fun savePass(networkPassbook: NetworkPassbook): Boolean {
        val passesList = getPasses().toMutableList()
        if (passesList.none { it.serialNumber == networkPassbook.serialNumber }) {
            passesList.add(networkPassbook)
            // Serialize and save
            sharedPrefs.edit().putString("passes", gson.toJson(passesList).toString()).commit()
            return true
        }
        return false
    }

    override fun getPasses(): List<NetworkPassbook> {
        val passesSerialized = sharedPrefs.getString("passes", "[]")
        return mutableListOf<NetworkPassbook>().apply {
            addAll(gson.fromJson(passesSerialized, Array<NetworkPassbook>::class.java))
        }
    }
}
