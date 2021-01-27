package com.app.homework8_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "KEY";
    private TextView noContactsTextView;
    private Intent intent;
    private DataAdapter adapter;
    private DataAdapter.OnContactClickListener onContactClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        //RecyclerView recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);

        SearchView searchView = findViewById(R.id.searchView);
        //noContactsTextView = findViewById(R.id.noContactsTextView);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootFragments, RecyclerListFragment.class, null)
                .commit();

        /*onContactClickListener = (contactBody, position) -> {
            intent = new Intent(MainActivity.this, EditContactActivity.class);
            intent.putExtra("edit pool", contactBody);
            intent.putExtra("position", position);
            startActivityForResult(intent, 2);
        }*/;

        /*findViewById(R.id.addContactButton).setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, AddContactActivity.class);
            startActivityForResult(intent, 1);
        });*/

        restoreDataAfterRotate(savedInstanceState);

        /*LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);*/

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    protected void restoreDataAfterRotate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            adapter = new DataAdapter(this,
                    (ArrayList<ContactBody>) savedInstanceState.getSerializable(KEY),
                    onContactClickListener);
            checkState();
        } else {
            adapter = new DataAdapter(this, new ArrayList<>(), onContactClickListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContactBody cb;
        if (resultCode == RESULT_OK && requestCode == 1) {
            assert data != null;
            cb = (ContactBody) data.getSerializableExtra("add_contact");
            adapter.add(cb);
        } else if (resultCode == RESULT_OK && requestCode == 2) {
            assert data != null;
            if (data.hasExtra("remove")) {
                int position = data.getIntExtra("remove", 0);
                Log.i("FFFF", String.valueOf(position));
                adapter.remove(position);
            } else if (data.hasExtra("edit pool")) {
                cb = (ContactBody) data.getSerializableExtra("edit");
                int position = intent.getIntExtra("position", 0);
                adapter.edit(cb, position);
            }
        }
        checkState();
    }

    private void checkState() {
        if (adapter.getItemCount() == 0) {
            noContactsTextView.setVisibility(View.VISIBLE);
        } else {
            noContactsTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            if (adapter.getItemCount() != 0) {
                outState.putSerializable(KEY, adapter.getContacts());
            } else {
                outState.putParcelableArrayList(KEY, new ArrayList<>());
            }
            checkState();
        }
    }

}
