package com.app.homework4;

public class ItemBody {
    private int imageID;
    private String name;
    private String numberOrEmail;

    public ItemBody(int imageID, String name, String numberOrEmail) {
        this.imageID = imageID;
        this.name = name;
        this.numberOrEmail = numberOrEmail;
    }

    public int getImageID() {
        return imageID;
    }

    public String getName() {
        return name;
    }

    public String getNumberOrEmail() {
        return numberOrEmail;
    }
}
