package com.example.homework5.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.R
import com.example.homework5.adapters.CarAdapter
import com.example.homework5.data.CarData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.database.CarsDatabase
import com.example.homework5.database.CarsDatabaseDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CarMainActivity : AppCompatActivity() {

    private lateinit var localAdapter: CarAdapter

    private lateinit var dao: CarsDatabaseDAO
    private lateinit var onEditButtonClick: CarAdapter.OnCarClickListener
    private lateinit var logoTextView: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var addActionButton: FloatingActionButton
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recycler = findViewById(R.id.recyclerView)
        addActionButton = findViewById(R.id.addNewCar)
        logoTextView = findViewById(R.id.listIsEmptyTextView)

        // инициализация БД
        dao = CarsDatabase.init(this).getCarDatabaseDAO()

        //  добавление новой машины
        addActionButton.setOnClickListener {
            val intent = Intent(this, AddCarActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // редактирование и информация о машине
        onEditButtonClick = object : CarAdapter.OnCarClickListener {
            override fun onCarClick(carData: CarData, position: Int, flag: Int) {
                when (flag) {
                    1 -> {
                        Intent(applicationContext, EditCarActivity::class.java).apply {
                            putExtra(Constants.POSITION_CAR_IN_DB, carData.id)
                            startActivityForResult(this, 2)
                        }
                    }
                    2 -> {
                        Intent(applicationContext, CarInfoActivity::class.java).apply {
                            putExtra(Constants.POSITION_CAR_IN_DB, carData.id)
                            startActivityForResult(this, 3)
                        }
                    }
                }
            }
        }

        // настройка ресайклера и адаптера
        val localLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        localAdapter = CarAdapter(this, ArrayList(), onEditButtonClick)
        recycler.apply {
            layoutManager = localLayoutManager
            adapter = localAdapter
        }
        checkDataBase()


    }

    private fun checkDataBase() {
        val carList = dao.getCarsList()
        if (carList.isNotEmpty()) {
            localAdapter.cars = carList as ArrayList<CarData>
            localAdapter.carsCopy = carList as ArrayList<CarData>
            localAdapter.sortByCarBrand()
            visibilityForLogoTextView()
        }
    }

    private fun visibilityForLogoTextView() {
        if (localAdapter.cars.isNotEmpty()) logoTextView.visibility = View.INVISIBLE
        else logoTextView.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkDataBase()
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

            override fun onQueryTextChange(newText: String): Boolean {
                localAdapter.filter?.filter(newText)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            searchView = item.actionView as SearchView
        }
        return super.onOptionsItemSelected(item)
    }

}