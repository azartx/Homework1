package com.example.homework5

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class CarMainActivity : AppCompatActivity() {

    private lateinit var adapter: CarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recycler: RecyclerView = findViewById(R.id.recyclerView)
        val addActionButton: FloatingActionButton = findViewById(R.id.addNewCar)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = CarAdapter(this, ArrayList<CarData>())
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        addActionButton.setOnClickListener{
            val intent = Intent(this, AddCarActivity::class.java)
            startActivityForResult(intent, 11)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.getParcelableExtra<CarData>("add")?.let { adapter.add(it) }

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