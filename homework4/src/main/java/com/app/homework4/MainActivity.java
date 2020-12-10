package com.app.homework4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView noContactsTextView;
    ArrayList<ContactBody> contacts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);

        noContactsTextView = findViewById(R.id.noContactsTextView);

        checkState();

        /*findViewById(R.id.itemBody).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts.get(v)
            }
        });*/






        findViewById(R.id.addContactButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        DataAdapter.OnContactClickListener onContactClickListener = new DataAdapter.OnContactClickListener() {
            @Override
            public void onContactClick(ContactBody contactBody) {
                Toast.makeText(MainActivity.this, contactBody.getContactName(), Toast.LENGTH_LONG).show();
                contactBody.setContactName("iiiiiii"); // имя установил, теперь нужно обновить холдер
            }
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        DataAdapter adapter = new DataAdapter(this, contacts, onContactClickListener);
        recyclerView.setAdapter(adapter);

        

    }

    private void setInitialData() {

        contacts.add(new ContactBody(R.drawable.ic_baseline_contact_phone_24,
                "Alexander Pushkin",
                "+375298885554"));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {

            ContactBody cb = (ContactBody) data.getSerializableExtra("add_contact");

            contacts.add(cb);
        }
        checkState();
    }

    private void checkState() {
        if (contacts.size() == 0) {
            noContactsTextView.setVisibility(View.VISIBLE);
        } else {
            noContactsTextView.setVisibility(View.INVISIBLE);
        }
    }

    public static void startActivity1(int position) {

    }

}
