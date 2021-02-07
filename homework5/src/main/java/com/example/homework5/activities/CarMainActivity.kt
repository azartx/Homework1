package com.example.homework5.activities

import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.add
import androidx.fragment.app.replace
import com.example.homework5.R
import com.example.homework5.adapters.CarAdapter
import com.example.homework5.data.staticData.Constants.Companion.CAR_ADD_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.CAR_EDIT_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.CAR_INFO_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.CAR_RECYCLE_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.WORK_ADD_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.WORK_EDIT_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.WORK_RECYCLE_FRAGMENT
import com.example.homework5.fragments.CarInfoFragment
import com.example.homework5.fragments.WorkRecycleFragment
import com.example.homework5.fragments.AddWorkFragment
import com.example.homework5.fragments.EditWorkFragment
import com.example.homework5.fragments.AddCarFragment
import com.example.homework5.fragments.ChangeFragmentListener
import com.example.homework5.fragments.EditCarFragment
import com.example.homework5.fragments.RecycleFragment
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class CarMainActivity : AppCompatActivity(), ChangeFragmentListener {

    private lateinit var localAdapter: CarAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_main)
        writeLog()
        supportFragmentManager.beginTransaction()
                .add<RecycleFragment>(R.id.rootFragment)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(chars: String): Boolean {
                localAdapter.filter.filter(chars)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun writeLog() {
        val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss",
                Locale.getDefault())
                .format(Date()).toString()

        openFileOutput("appLog.txt", MODE_APPEND).apply {
            write(("\n" + time).toByteArray())
            close()
        }
    }

    override fun onChangeFragment(id: Int, bundle: Bundle?) {
        when(id){
            CAR_RECYCLE_FRAGMENT -> supportFragmentManager.beginTransaction()
                    .replace<RecycleFragment>(R.id.rootFragment, null, bundle)
                    .commit()
            CAR_ADD_FRAGMENT -> supportFragmentManager.beginTransaction()
                    .replace<AddCarFragment>(R.id.rootFragment, null, bundle)
                    .addToBackStack(null)
                    .commit()
            CAR_EDIT_FRAGMENT -> supportFragmentManager.beginTransaction()
                    .replace<EditCarFragment>(R.id.rootFragment, null, bundle)
                    .addToBackStack(null)
                    .commit()
            CAR_INFO_FRAGMENT -> supportFragmentManager.beginTransaction()
                    .replace<CarInfoFragment>(R.id.rootFragment, null, bundle)
                    .addToBackStack(null)
                    .commit()
            WORK_RECYCLE_FRAGMENT -> supportFragmentManager.beginTransaction()
                    .replace<WorkRecycleFragment>(R.id.rootFragment, null, bundle)
                    .addToBackStack(null)
                    .commit()
            WORK_ADD_FRAGMENT -> supportFragmentManager.beginTransaction()
                    .replace<AddWorkFragment>(R.id.rootFragment, null, bundle)
                    .addToBackStack(null)
                    .commit()
            WORK_EDIT_FRAGMENT -> supportFragmentManager.beginTransaction()
                    .replace<EditWorkFragment>(R.id.rootFragment, null, bundle)
                    .addToBackStack(null)
                    .commit()
        }
    }

}