package com.example.homework5.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity/*(foreignKeys = [ForeignKey(entity = CarData::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = CASCADE)])*/
class WorkData(@ColumnInfo val workName: String,
               @ColumnInfo val workDescription: String,
               @ColumnInfo val time: String,
               @ColumnInfo val progress: String,
               @ColumnInfo val coast: String,
               @ColumnInfo val color: String,
               @ColumnInfo val positionInCarList: Int) : Parcelable {

   @PrimaryKey() var id: String = UUID.randomUUID().toString()

    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(workName)
        parcel.writeString(workDescription)
        parcel.writeString(time)
        parcel.writeString(progress)
        parcel.writeString(coast)
        parcel.writeString(color)
        parcel.writeInt(positionInCarList)
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