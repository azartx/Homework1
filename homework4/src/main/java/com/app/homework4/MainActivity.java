package com.app.homework4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ContactBody> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);

        findViewById(R.id.addContactButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });


        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // создаем адаптер
        DataAdapter adapter = new DataAdapter(this, contacts);
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData() {

        contacts.add(new ContactBody(R.drawable.ic_baseline_contact_phone_24,
                "Alexander Pushkin",
                "+375298885554"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "aaaaaDavid Blame",
                "blame.asdasd@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "asdasdgg.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.dfgdfgdfg@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));
        contacts.add(new ContactBody(R.drawable.ic_baseline_email_24,
                "David Blame",
                "blame.david@gmail.com"));

    }

}
