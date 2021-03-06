package com.example.homework5.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CarData(@ColumnInfo val carOwnerName: String,
              @ColumnInfo val carModelName: String,
              @ColumnInfo val carGosNumber: String,
              @ColumnInfo val carImage: String?) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long? = null

    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString()) {

        parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(carOwnerName)
        parcel.writeString(carModelName)
        parcel.writeString(carGosNumber)
        parcel.writeString(carImage)
        parcel.writeLong(id ?: -1)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CarData> {
        override fun createFromParcel(parcel: Parcel): CarData {
            return CarData(parcel)
        }

        override fun newArray(size: Int): Array<CarData?> {
            return arrayOfNulls(size)
        }
    }

}