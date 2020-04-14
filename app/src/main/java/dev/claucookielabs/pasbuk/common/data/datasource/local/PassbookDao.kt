package dev.claucookielabs.pasbuk.common.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import dev.claucookielabs.pasbuk.common.data.datasource.local.model.DbPassbook

@Dao
interface PassbookDao {
    @Query("SELECT * FROM DbPassbook")
    fun getAllPasses(): List<DbPassbook>

    @Insert(onConflict = REPLACE)
    fun savePass(passbook: DbPassbook)
}
