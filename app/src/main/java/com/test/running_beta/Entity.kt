package com.test.running_beta

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserInfo")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val name: Long = 0L,
    @ColumnInfo
    val phoneNumber: String,
    @ColumnInfo
    val id: String,
    @ColumnInfo
    val password: String,
    @ColumnInfo
    val gender: String
)
