package com.app.homework4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;

public class AddContactActivity extends AppCompatActivity {

    private RadioButton numberRadioButton;
    private RadioButton emailRadioButton;
    private EditText nameEditText;
    private EditText numberOrEmailEditText;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = findViewById(R.id.toolbarAddContact);
        setSupportActionBar(toolbar);

        Intent intent = new Intent();

        numberRadioButton = findViewById(R.id.numberRadioButton);
        numberOrEmailEditText = findViewById(R.id.numberOrEmailEditText);
        nameEditText = findViewById(R.id.nameEditText);
        emailRadioButton = findViewById(R.id.emailRadioButton);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        findViewById(R.id.backButton).setOnClickListener(v -> {
            setResult(RESULT_CANCELED, intent);
            finish();
        });

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            switch (i) {
                case R.id.numberRadioButton:
                    numberOrEmailEditText.setHint(R.string.number);
                    break;
                case R.id.emailRadioButton:
                    numberOrEmailEditText.setHint(R.string.email);
                    break;
            }
        });

        findViewById(R.id.submitButton).setOnClickListener(v -> {
            int image = 0;
            String name;
            String numberOrEmail;

            name = nameEditText.getText().toString();
            numberOrEmail = numberOrEmailEditText.getText().toString();

            boolean check = true;

            if (numberRadioButton.isChecked()) {
                image = R.drawable.ic_baseline_contact_phone_24;
            } else if (emailRadioButton.isChecked()) {
                image = R.drawable.ic_baseline_email_24;
            } else {
                Toast.makeText(getApplicationContext(), "Set phone or email", Toast.LENGTH_LONG).show();
                check = false;
            }

            check = checkPoolIsFill(name, numberOrEmail, check);

            finishActivity(intent, image, name, numberOrEmail, check);

        });

    }

    private void finishActivity(Intent intent, int image, String name, String numberOrEmail, boolean check) {
        if (check) {
            ContactBody cb = new ContactBody(image, name, numberOrEmail);
            intent.putExtra("add_contact", (Serializable) cb);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean checkPoolIsFill(String name, String numberOrEmail, boolean check) {
        if (name.length() < 1 || numberOrEmail.length() < 1) {
            Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_LONG).show();
            check = false;
        }
        return check;
    }

}