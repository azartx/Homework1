package com.example.homework5.data

import android.os.Parcel
import android.os.Parcelable

class WorkData(val typeOfWork: String,
               val time: String,
               val progress: String,
               val coast: String,
               val color: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(typeOfWork)
        parcel.writeString(time)
        parcel.writeString(progress)
        parcel.writeString(coast)
        parcel.writeInt(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorkData> {
        override fun createFromParcel(parcel: Parcel): WorkData {
            return WorkData(parcel)
        }

        override fun newArray(size: Int): Array<WorkData?> {
            return arrayOfNulls(size)
        }
    }
}