package com.example.homework5

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

class CarData(val carOwnerName: String?,
              val carModelName: String?,
              val carGosNumber: String?,
              val carImage: Bitmap?) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable<Bitmap?>(Bitmap::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(carOwnerName)
        parcel.writeString(carModelName)
        parcel.writeString(carGosNumber)
        parcel.writeParcelable(carImage, flags)
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