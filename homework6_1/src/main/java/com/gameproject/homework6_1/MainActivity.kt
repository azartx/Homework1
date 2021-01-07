
package com.gameproject.homework6_1

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var logoTextView: TextView
    private var parentCar: String? = null
    private var carId: Long = 0
    private lateinit var localAdapter: WorksAdapter
    private lateinit var editWorkListener: WorksAdapter.OnWorkClickListener
    private lateinit var dao: WorksDatabaseDAO

    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView
    private lateinit var addWorkActionButton: FloatingActionButton
    private lateinit var carNameInToolbar: TextView
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        carNameInToolbar = findViewById(R.id.carNameInToolbar)
        setSupportActionBar(toolbar)

        recycler = findViewById(R.id.recyclerView)
        addWorkActionButton = findViewById(R.id.addNewWork)
        backButton = findViewById(R.id.backButton)
        logoTextView = findViewById(R.id.worksIsEmptyTextView)

        getIntentData(intent, carNameInToolbar)

        val path = "com.example.homework5.fileprovider.database"
        val resolver = contentResolver.query(Uri.parse(path), null,null,null,null)!!.close()
        val workList = arrayListOf<>()


        // инициализация БД
        dao = CarsDatabase.init(this).getWorkDatabaseDAO()

       /* // нажата кнопка ДОБАВИТЬ РАБОТУ
        addWorkActionButton.setOnClickListener {
            Intent(this, AddWorkActivity::class.java).apply {
                putExtra(PARENT_CAR, parentCar)
                startActivityForResult(this, 1)
            }
        }

        // нажатие на работу
        editWorkListener = object : WorksAdapter.OnWorkClickListener {
            override fun onWorkClick(workData: WorkData, position: Int) {
                Intent(applicationContext, EditWorkActivity::class.java).apply {
                    putExtra(Constants.POSITION_CAR_IN_DB, workData.id)
                    startActivityForResult(this, 2)
                }
            }
        }*/

        // нажата кнопка назад
        backButton.setOnClickListener { finish() }

        // настройка ресайклера и адаптера
        val localLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        localAdapter = WorksAdapter(this, ArrayList(), editWorkListener)
        recycler.apply {
            layoutManager = localLayoutManager
            adapter = localAdapter
        }
        checkDataBase()

    }

    private fun visibilityForLogoTextView() {
        if (localAdapter.works.isNotEmpty()) logoTextView.visibility = View.INVISIBLE
        else logoTextView.visibility = View.VISIBLE
    }

    private fun checkDataBase() {
        val workList = dao.getParentWorks(parentCar)
        if (workList.isNotEmpty()) {
            localAdapter.works = workList as ArrayList<WorkData>
            visibilityForLogoTextView()
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
