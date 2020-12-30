package com.example.homework5.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.R
import com.example.homework5.adapters.WorkAdapter
import com.example.homework5.data.CarData
import com.example.homework5.data.WorkData
import com.example.homework5.database.CarsDatabase
import com.example.homework5.database.WorksDatabaseDAO

import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class WorksActivity : AppCompatActivity() {

    private var carObject: CarData? = null
    private var carPosition: Int = 0
    private lateinit var adapter: WorkAdapter
    private lateinit var editWorkListener: WorkAdapter.OnWorkClickListener
    private lateinit var dao: WorksDatabaseDAO

    companion object {
        const val OBJECT = "editCar"
        const val POSITION = "editPosition"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_works)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val carNameInToolbar = findViewById<TextView>(R.id.carNameInToolbar)
        setSupportActionBar(toolbar)

        val recycler: RecyclerView = findViewById(R.id.recyclerView)
        val addWorkActionButton = findViewById<FloatingActionButton>(R.id.addNewWork)
        val backButton: ImageView = findViewById(R.id.backButton)

        val intent = intent

        getIntentData(intent, carNameInToolbar)

        // инициализация БД
        dao = CarsDatabase.init(this).getWorkDatabaseDAO()

        // нажата кнопка ДОБАВИТЬ РАБОТУ
        addWorkActionButton.setOnClickListener {
            val addWorkIntent = Intent(this, AddWorkActivity::class.java)
            addWorkIntent.putExtra(OBJECT, carObject)
            addWorkIntent.putExtra(POSITION, carPosition)
            startActivityForResult(addWorkIntent, 1)
        }

        // нажатие на работу
        editWorkListener = object : WorkAdapter.OnWorkClickListener {
            override fun onWorkClick(workData: WorkData, position: Int) {
                val editWorkIntent = Intent(applicationContext, EditWorkActivity::class.java)
                editWorkIntent.putExtra(OBJECT, workData)
                editWorkIntent.putExtra(POSITION, position)
                startActivityForResult(editWorkIntent, 2)
            }
        }

        // нажата кнопка назад
        backButton.setOnClickListener { finish() }

        // настройка ресайклера и адаптера
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter = WorkAdapter(this, ArrayList(), editWorkListener)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        checkDataBase()

    }

    private fun checkDataBase() {
        val workList = dao.getParentWorks(carObject!!.carModelName)
        if (workList.isNotEmpty()) {
            adapter.works = workList as ArrayList<WorkData>
            adapter.notifyDataSetChanged()
        }
    }

    private fun getIntentData(intent: Intent, carNameInToolbar: TextView) {
        carObject = intent.getParcelableExtra(OBJECT)
        carPosition = intent.getIntExtra(POSITION, 0)

        carNameInToolbar.text = carObject?.carModelName ?: getString(R.string.car_works)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            data?.getParcelableExtra<WorkData>(OBJECT)?.let {
                it.parentCar = carObject!!.carModelName
                it.id = UUID.randomUUID().toString()
                adapter.add(it)
                dao.addCarToDatabase(it)
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            val position = data?.getIntExtra(POSITION, 0)
            data?.getParcelableExtra<WorkData>(OBJECT)?.let {

                it.parentCar = carObject!!.carModelName
                adapter.edit(it, position!!)
            }
        }

    }

}