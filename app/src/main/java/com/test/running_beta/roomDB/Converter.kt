package com.test.running_beta.roomDB

import androidx.room.TypeConverter
import com.google.gson.Gson


class RecordsConverter {

    //gson library 를 소환
    private val gson = Gson()

    @TypeConverter
    fun fromRecords(records: Records): String {
        return gson.toJson(records)
    }

    @TypeConverter
    fun toRecords(json: String): Records {
        return gson.fromJson(json, Records::class.java)
    }
}
