package com.example.homework5

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class CarMainActivity : AppCompatActivity() {

    private lateinit var adapter: CarAdapter

    //    private lateinit var dao: CarsDatabaseDAO
    private lateinit var onEditButtonClick: CarAdapter.OnCarClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recycler: RecyclerView = findViewById(R.id.recyclerView)
        val addActionButton: FloatingActionButton = findViewById(R.id.addNewCar)

        var intent: Intent

        // инициализация БД
        // dao = CarsDatabase.init(this).getCarDatabaseDAO()


        //  добавление новой машины
        addActionButton.setOnClickListener {
            intent = Intent(this, AddCarActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // редактирование машины
        onEditButtonClick = object : CarAdapter.OnCarClickListener {

            override fun onCarClick(carData: CarData, position: Int) {
                intent = Intent(applicationContext, EditCarActivity::class.java)
                startActivityForResult(intent, 2)
            }
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = CarAdapter(this, ArrayList<CarData>(), onEditButtonClick)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lateinit var t: CarData // ?????
        data?.getParcelableExtra<CarData>("add")?.let {
            adapter.add(it)
            t = it
        }

        /*dao.addCarToDatabase(t.carGosNumber.toString())
        val rrr = dao.getCarsList()
        Log.i("RRR", rrr.toString())*/

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            val searchView = item.actionView as SearchView
        }
        return super.onOptionsItemSelected(item)
    }

}