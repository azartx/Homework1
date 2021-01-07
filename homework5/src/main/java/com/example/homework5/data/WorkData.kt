package com.example.homework5.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity/*(foreignKeys = [ForeignKey(entity = CarData::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = CASCADE)])*/
class WorkData(@ColumnInfo val workName: String,
               @ColumnInfo val workDescription: String,
               @ColumnInfo val time: String,
               @ColumnInfo val progress: String,
               @ColumnInfo val coast: String,
               @ColumnInfo val color: Int) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long? = null
    @ColumnInfo
    var parentCar: String? = null

    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readInt()) {
        parcel.readLong()
        parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(workName)
        parcel.writeString(workDescription)
        parcel.writeString(time)
        parcel.writeString(progress)
        parcel.writeString(coast)
        parcel.writeInt(color)
        parcel.writeLong(id ?: -1)
        parcel.writeString(parentCar)
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