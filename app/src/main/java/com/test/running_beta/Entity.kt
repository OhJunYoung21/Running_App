package com.test.running_beta

import android.net.ipsec.ike.exceptions.InvalidMajorVersionException
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson


@Entity(tableName = "UserInfo")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val key: Long = 0L,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val phoneNumber: String,
    @ColumnInfo
    val id: String,
    @ColumnInfo
    val password: String,
    @ColumnInfo
    val gender: String,
    @ColumnInfo
    val record: Records

)

data class Records(
    val date: Long,
    val time: Long,
    val speed: Long,
    val distance: Long
)




