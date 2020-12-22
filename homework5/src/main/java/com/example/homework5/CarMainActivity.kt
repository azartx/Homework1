package com.example.homework5

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CarMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_main)

        val recycler: RecyclerView = findViewById(R.id.recyclerView)


        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = CarAdapter(this, ArrayList<CarData>())
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))
        adapter.cars.add(CarData("Hello", "goodby", "hehr 44", R.drawable.car1))


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