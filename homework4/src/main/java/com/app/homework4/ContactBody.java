package com.app.homework4;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ContactBody implements Serializable, Parcelable {

    private static final long serialVersionUID = 1L;
    private int image;
    private String contactName;
    private String emailOrNumber;

    public ContactBody(int image, String contactName, String emailOrNumber) {
        this.image = image;
        this.contactName = contactName;
        this.emailOrNumber = emailOrNumber;

    }

    private ContactBody(Parcel in) {
        image = in.readInt();
        contactName = in.readString();
        emailOrNumber = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(contactName);
        dest.writeString(emailOrNumber);
    }
}
