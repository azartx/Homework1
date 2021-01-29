package com.app.homework8_1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ContactBody implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo private Long id;
    @ColumnInfo private int image;
    @ColumnInfo private String contactName;
    @ColumnInfo private String emailOrNumber;

    public ContactBody(int image, String contactName, String emailOrNumber) {
        this.image = image;
        this.contactName = contactName;
        this.emailOrNumber = emailOrNumber;
    }

    private ContactBody(Parcel in) {
        id = in.readLong();
        image = in.readInt();
        contactName = in.readString();
        emailOrNumber = in.readString();
    }

    public static final Creator<ContactBody> CREATOR = new Creator<ContactBody>() {
        @Override
        public ContactBody createFromParcel(Parcel in) {
            return new ContactBody(in);
        }

        @Override
        public ContactBody[] newArray(int size) {
            return new ContactBody[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmailOrNumber() {
        return emailOrNumber;
    }

    public void setEmailOrNumber(String emailOrNumber) {
        this.emailOrNumber = emailOrNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(image);
        dest.writeString(contactName);
        dest.writeString(emailOrNumber);
    }
}
