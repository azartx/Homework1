package com.app.homework8_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditContactActivity extends AppCompatActivity {

    private int position;
    private ContactBody actualData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Toolbar toolbar = findViewById(R.id.toolbarEditContact);
        setSupportActionBar(toolbar);

        EditText name = findViewById(R.id.nameEditEditText);
        EditText emailOfPhone = findViewById(R.id.numberOrEmailEditEditText);

        Intent intent;

        if (savedInstanceState == null) {
            intent = getIntent();
            actualData = (ContactBody) intent.getSerializableExtra("edit pool");
            position = intent.getIntExtra("position", 0);
        } else {
            intent = new Intent();
            actualData = savedInstanceState.getParcelable("object");
            position = savedInstanceState.getInt("position");
        }

        getIntentData(name, emailOfPhone, actualData);

        findViewById(R.id.backButton).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        // нажатие кнопки сабмит
        findViewById(R.id.submitButton).setOnClickListener(v -> {
            actualData.setContactName(name.getText().toString());
            actualData.setEmailOrNumber(emailOfPhone.getText().toString());
            finishActivity(intent);
        });

        // нажатие кнопки remove
        findViewById(R.id.removeContactButton).setOnClickListener(v -> {
            Intent removeIntent = new Intent().putExtra("remove", position);
            Log.i("FFFF", String.valueOf(position));
            setResult(RESULT_OK, removeIntent);
            finish();
        });

    }

    private void finishActivity(Intent intent) {
        intent.putExtra("edit", actualData);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getIntentData(EditText name, EditText emailOfPhone, ContactBody actualData) {
        name.setText(actualData.getContactName());
        emailOfPhone.setText(actualData.getEmailOrNumber());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("position", position);
        savedInstanceState.putParcelable("object", actualData);

        super.onSaveInstanceState(savedInstanceState);
    }
}