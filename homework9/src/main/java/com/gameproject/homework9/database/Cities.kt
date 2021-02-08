package com.gameproject.homework9.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cities(
        @ColumnInfo val city: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var cityId: Int = 0
    @ColumnInfo
    var flag: Boolean = false
}