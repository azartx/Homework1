package com.app.homework8_1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.homework8_1.fragments.AddContactFragment;
import com.app.homework8_1.fragments.EditContactFragment;
import com.app.homework8_1.fragments.RecyclerListFragment;
import com.app.homework8_1.utils.ChangeFragmentListener;

import static com.app.homework8_1.db.SingletonDatabase.contactDB;
import static com.app.homework8_1.db.SingletonDatabase.getDB;
import static com.app.homework8_1.utils.Constants.ADD_CONTACT_FRAGMENT;
import static com.app.homework8_1.utils.Constants.EDIT_CONTACT_FRAGMENT;
import static com.app.homework8_1.utils.Constants.RECYCLER_LIST_FRAGMENT;

public class MainActivity extends AppCompatActivity implements ChangeFragmentListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        getDB(getApplicationContext()); //singletonDB (SingletonDatabase in ContactsRepository)
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootFragments, RecyclerListFragment.class, null)
                .commit();
    }

    @Override
    public void onChangeFragment(int id, Bundle args) {
        switch (id) {
            case RECYCLER_LIST_FRAGMENT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rootFragments, RecyclerListFragment.class, null)
                        .commit();
                break;
            case ADD_CONTACT_FRAGMENT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rootFragments, AddContactFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                break;
            case EDIT_CONTACT_FRAGMENT:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rootFragments, EditContactFragment.class, args)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        contactDB.closeDB();
    }
}
