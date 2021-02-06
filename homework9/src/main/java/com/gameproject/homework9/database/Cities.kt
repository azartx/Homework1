package com.gameproject.homework9.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Cities(
        @ColumnInfo val city: String
)

@PrimaryKey(autoGenerate = true)
@ColumnInfo
val id: Int = 0