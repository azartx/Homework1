package com.app.homework4;

import android.content.Intent;
import android.os.Bundle;
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
        int position = intent.getIntExtra("position", 0);

        getIntentData(name, emailOfPhone, actualData);

        findViewById(R.id.backButton).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        findViewById(R.id.submitButton).setOnClickListener(v -> {
            if (name != null && emailOfPhone != null) {
                actualData.setContactName(name.getText().toString());
                actualData.setEmailOrNumber(emailOfPhone.getText().toString());

                intent.putExtra("edit pool", (Serializable) actualData);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        findViewById(R.id.removeContactButton).setOnClickListener(v -> {
            intent.putExtra("edit pool2", (Serializable) actualData);
            intent.putExtra("position", position);
            setResult(RESULT_OK, intent);
            finish();
        });

    }

    private void getIntentData(EditText name, EditText emailOfPhone, ContactBody actualData) {
        name.setText(actualData.getContactName());
        emailOfPhone.setText(actualData.getEmailOrNumber());
    }
}