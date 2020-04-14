package dev.claucookielabs.pasbuk.common.data.datasource.local.model

import androidx.room.TypeConverter
import com.google.gson.Gson


class InfoFieldsConverter {
    @TypeConverter
    fun fromSerialized(fieldsSerialized: String): List<DbInfoField> {
        return Gson().fromJson(fieldsSerialized, Array<DbInfoField>::class.java).toList()
    }

    @TypeConverter
    fun toList(fields: List<DbInfoField>): String {
        return if (fields.isEmpty()) "[]"
        else Gson().toJson(fields)
    }
}
