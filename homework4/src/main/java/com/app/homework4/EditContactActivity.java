package com.app.homework4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditContactActivity extends AppCompatActivity {

    public static final String CONTACT_ID = "contactId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
    }
}