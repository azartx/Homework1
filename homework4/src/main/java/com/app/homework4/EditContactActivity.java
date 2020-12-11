package com.app.homework4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;

public class EditContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Toolbar toolbar = findViewById(R.id.toolbarEditContact);
        setSupportActionBar(toolbar);

        EditText name = findViewById(R.id.nameEditEditText);
        EditText emailOfPhone = findViewById(R.id.numberOrEmailEditEditText);


        Intent intent = getIntent();
        ContactBody actualData = (ContactBody) intent.getSerializableExtra("edit pool");
        getIntentData(name, emailOfPhone, intent, actualData);

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name != null && emailOfPhone != null) {
                    actualData.setContactName(name.getText().toString());
                    actualData.setEmailOrNumber(emailOfPhone.getText().toString());

                    intent.putExtra("edit pool", (Serializable) actualData);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    private void getIntentData(EditText name, EditText emailOfPhone, Intent intent, ContactBody actualData) {
        name.setText(actualData.getContactName());
        emailOfPhone.setText(actualData.getEmailOrNumber());
    }
}