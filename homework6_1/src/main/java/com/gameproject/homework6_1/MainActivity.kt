package com.gameproject.homework6_1

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val path = "content://com.example.homework5.adapters.CarWorksProvider/database"
    private lateinit var logoTextView: TextView
    private lateinit var localAdapter: WorksAdapter

    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView
    private lateinit var carNameInToolbar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        carNameInToolbar = findViewById(R.id.carNameInToolbar)
        setSupportActionBar(toolbar)

        recycler = findViewById(R.id.recyclerView)
        logoTextView = findViewById(R.id.worksIsEmptyTextView)

        // настройка ресайклера и адаптера
        recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = localAdapter
        }
        getDataFromProvider()

    }

    private fun getDataFromProvider() {
        val cursor = contentResolver.query(Uri.parse(path), null, null, null, null)
        cursor?.run {
            moveToFirst()
            localAdapter.apply {
                works.add(WorkData(getString(getColumnIndex("workName")),
                        getString(getColumnIndex("workDescription")),
                        getString(getColumnIndex("time")),
                        getString(getColumnIndex("progress")),
                        getString(getColumnIndex("coast")),
                        getInt(getColumnIndex("color"))))
                notifyDataSetChanged()
            }
            cursor.close()
            visibilityForLogoTextView()
        }
    }

    private fun visibilityForLogoTextView() {
        if (localAdapter.works.isNotEmpty()) logoTextView.visibility = View.INVISIBLE
        else logoTextView.visibility = View.VISIBLE
    }

}