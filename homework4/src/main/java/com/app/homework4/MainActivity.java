package com.app.homework4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView noContactsTextView;
    ArrayList<ContactBody> contacts = new ArrayList<>();
    RecyclerView recyclerView;
    Intent intent;
    ContactBody cb;
    DataAdapter adapter;
    LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);

        noContactsTextView = findViewById(R.id.noContactsTextView);

        checkState();

        findViewById(R.id.addContactButton).setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(intent, 1);
        });

        DataAdapter.OnContactClickListener onContactClickListener = (contactBody, position) -> {

            Log.i("TTT", String.valueOf(position));
            intent = new Intent(MainActivity.this, EditContactActivity.class);
            intent.putExtra("edit pool", (Serializable) contactBody);
            intent.putExtra("position", position);
            startActivityForResult(intent, 2);

        };

        adapter = new DataAdapter(this, contacts, onContactClickListener);
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == 1) {

            cb = (ContactBody) data.getSerializableExtra("add_contact");
            contacts.add(cb);

        } else if (resultCode == RESULT_OK && requestCode == 2) {
            if (data.hasExtra("edit pool2")) {

                int position = intent.getIntExtra("position", 0);
                contacts.remove(position - 1);
                adapter.notifyDataSetChanged();

            } else if (data.hasExtra("edit pool")) {

                cb = (ContactBody) data.getSerializableExtra("edit pool");
                int position  = intent.getIntExtra("position", 0);
                contacts.set(position - 1, cb);
                adapter.notifyDataSetChanged();

            }
        }
        checkState();
    }

    private void checkState() { // проверяем длинну списка, устанавливаем видимость textView
        if (contacts.size() == 0) {
            noContactsTextView.setVisibility(View.VISIBLE);
        } else {
            noContactsTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        savedInstanceState.getParcelableArrayList("saveKey");
    }

}
