package com.example.homework5

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

@Entity
class CarsInfoDatabase(
        @PrimaryKey @ColumnInfo val id: Long,
        @ColumnInfo val ownerName: String,
        @ColumnInfo val carName: String,
        @ColumnInfo val gosNumber: String) {
}