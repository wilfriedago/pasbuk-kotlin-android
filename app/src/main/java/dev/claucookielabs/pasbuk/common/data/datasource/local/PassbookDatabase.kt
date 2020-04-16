package dev.claucookielabs.pasbuk.common.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.claucookielabs.pasbuk.common.data.datasource.local.model.DbPassbook
import dev.claucookielabs.pasbuk.common.data.datasource.local.model.InfoFieldsConverter

@Database(version = 1, entities = [DbPassbook::class])
@TypeConverters(InfoFieldsConverter::class)
abstract class PassbookDatabase : RoomDatabase() {
    abstract fun passbookDao(): PassbookDao
}
