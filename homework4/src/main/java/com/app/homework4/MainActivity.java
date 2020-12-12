package com.app.homework4;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_RECYCLER_STATE = "KEY";
    TextView noContactsTextView;
    ArrayList<ContactBody> contacts = new ArrayList<>();
    RecyclerView recyclerView;
    Intent intent;
    ContactBody cb;
    DataAdapter adapter;
    LinearLayoutManager manager;
    Bundle mBundleRecyclerViewState;

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

        // кастомным кликером забираю позицию и сам объект по нажатию на вью, потом передаю
        // позицию и объект на обработку в EditContactActivity
        // ps. позицию передаю просто что бы она потом вернулась обратно и я смог установить ее
        // в методе онАктивитиРезалт
        DataAdapter.OnContactClickListener onContactClickListener = (contactBody, position) -> {

            Log.i("TTT", String.valueOf(position));
            intent = new Intent(MainActivity.this, EditContactActivity.class);
            intent.putExtra("edit pool", (Serializable) contactBody);
            intent.putExtra("position", position);
            startActivityForResult(intent, 2);

        };

        adapter = new DataAdapter(this, contacts, onContactClickListener);
        manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }


    // к слову, почему то тоже не отрабатывает......
    @Override
    protected void onPause()
    {
        super.onPause();

        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = manager.onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            manager.onRestoreInstanceState(listState);
        }
    }


    // тут я принимаю интэнт и устанавливаю значения в список (удаляю, изменяю, добавляю))
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {

            cb = (ContactBody) data.getSerializableExtra("add_contact");
            contacts.add(cb);

        } else if (resultCode == RESULT_OK && requestCode == 2) {
            if (data.hasExtra("edit pool2")) {

                cb = (ContactBody) data.getSerializableExtra("remove");
                int position = intent.getIntExtra("position", 0);
                contacts.remove(position - 1);
                adapter.notifyDataSetChanged();

            } else if (data.hasExtra("edit pool")) {

                cb = (ContactBody) data.getSerializableExtra("edit");
                int position  = intent.getIntExtra("position", 0);
                contacts.set(position - 1, cb);
                adapter.notifyDataSetChanged();

            }
        }
        checkState();
    }

    private void checkState() { // проверяем длинну списка, устанавливаем видимость  textView
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
