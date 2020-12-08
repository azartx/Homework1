package com.app.homework4;

public class ContactBody {

    private int image;
    private String contactName;
    private String emailOrNumber;

    public ContactBody(int image, String contactName, String emailOrNumber) {
        this.image = image;
        this.contactName = contactName;
        this.emailOrNumber = emailOrNumber;

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
}
