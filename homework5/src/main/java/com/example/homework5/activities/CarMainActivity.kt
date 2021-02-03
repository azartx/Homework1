package com.example.homework5.activities

import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.add
import androidx.fragment.app.replace
import com.example.homework5.R
import com.example.homework5.adapters.CarAdapter
import com.example.homework5.data.staticData.Constants.Companion.CAR_ADD_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.CAR_EDIT_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.CAR_INFO_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.CAR_RECYCLE_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.WORK_RECYCLE_FRAGMENT
import com.example.homework5.fragments.*
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class CarMainActivity : AppCompatActivity(), ChangeFragmentListener {

    private lateinit var localAdapter: CarAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_main)
        /*toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)*/
        writeLog()
        supportFragmentManager.beginTransaction()
                .add<RecycleFragment>(R.id.rootFragment)
                .commit()



        /*recycler = findViewById(R.id.recyclerView)
        addActionButton = findViewById(R.id.addNewCar)
        logoTextView = findViewById(R.id.listIsEmptyTextView)*/

        /*// инициализация БД
        databaseRepository = DatabaseRepository(applicationContext)

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
            }*/
        }

        /*// настройка ресайклера и адаптера
        localAdapter = CarAdapter(this, ArrayList(), onEditButtonClick)
        recycler.apply {
            layoutManager = LinearLayoutManager(this@CarMainActivity,
                    RecyclerView.VERTICAL, false)
            adapter = localAdapter
        }
        checkDataBase()
    }*/

    /*private fun checkDataBase() {
        databaseRepository.mainScope().launch {
            val carList = databaseRepository.getCarsList()
            if (carList.isNotEmpty()) {
                localAdapter.apply {
                    cars = carList as ArrayList<CarData>
                    carsCopy = carList
                    sortByCarBrand()
                    notifyDataSetChanged() // почему то без этого не обновляет адекватно после применения асинхрона
                }
                visibilityForLogoTextView()
            }
        }
    }*/

    /*private fun visibilityForLogoTextView() {
        if (localAdapter.cars.isNotEmpty()) logoTextView.visibility = View.INVISIBLE
        else logoTextView.visibility = View.VISIBLE
    }*/

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkDataBase()
    }*/

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
        }
    }


}