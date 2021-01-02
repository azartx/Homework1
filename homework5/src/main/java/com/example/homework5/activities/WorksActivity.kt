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
import com.example.homework5.data.WorkData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.data.staticData.Constants.Companion.PARENT_CAR
import com.example.homework5.database.CarsDatabase
import com.example.homework5.database.WorksDatabaseDAO

import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class WorksActivity : AppCompatActivity() {

    private var parentCar: String? = null
    private var carId: Long = 0
    private lateinit var localAdapter: WorkAdapter
    private lateinit var editWorkListener: WorkAdapter.OnWorkClickListener
    private lateinit var dao: WorksDatabaseDAO

    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView
    private lateinit var addWorkActionButton: FloatingActionButton
    private lateinit var carNameInToolbar: TextView
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_works)
        toolbar = findViewById(R.id.toolbar)
        carNameInToolbar = findViewById(R.id.carNameInToolbar)
        setSupportActionBar(toolbar)

        recycler = findViewById(R.id.recyclerView)
        addWorkActionButton = findViewById(R.id.addNewWork)
        backButton = findViewById(R.id.backButton)

        getIntentData(intent, carNameInToolbar)

        // инициализация БД
        dao = CarsDatabase.init(this).getWorkDatabaseDAO()

        // нажата кнопка ДОБАВИТЬ РАБОТУ
        addWorkActionButton.setOnClickListener {
            Intent(this, AddWorkActivity::class.java).apply {
                putExtra(PARENT_CAR, parentCar)
                startActivityForResult(this, 1)
            }
        }

        // нажатие на работу
        editWorkListener = object : WorkAdapter.OnWorkClickListener {
            override fun onWorkClick(workData: WorkData, position: Int) {
                Intent(applicationContext, EditWorkActivity::class.java).apply {
                    //putExtra(Constants.OBJECT, workData)
                    putExtra(Constants.POSITION_CAR_IN_DB, workData.id)
                    startActivityForResult(this, 2)
                }
            }
        }

        // нажата кнопка назад
        backButton.setOnClickListener { finish() }

        // настройка ресайклера и адаптера
        val localLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        localAdapter = WorkAdapter(this, ArrayList(), editWorkListener)
        recycler.apply {
            layoutManager = localLayoutManager
            adapter = localAdapter
        }
        checkDataBase()

    }

    private fun checkDataBase() {
        val workList = dao.getParentWorks(parentCar)
        if (workList.isNotEmpty()) {
            localAdapter.works = workList as ArrayList<WorkData>
            localAdapter.notifyDataSetChanged()
        }
    }

    private fun getIntentData(intent: Intent, carNameInToolbar: TextView) {
        carId = intent.getLongExtra(Constants.POSITION_CAR_IN_DB, 0)
        parentCar = intent.getStringExtra("modelName")

        carNameInToolbar.text = parentCar ?: getString(R.string.car_works)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkDataBase()
    }

}