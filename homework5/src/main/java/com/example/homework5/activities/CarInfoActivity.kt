package com.example.homework5.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.example.homework5.R
import com.example.homework5.data.CarData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.database.DatabaseRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class CarInfoActivity : AppCompatActivity() {

    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var carObject: CarData
    private var carId: Long = 0
    private lateinit var image: ImageView
    private lateinit var ownerName: TextView
    private lateinit var carName: TextView
    private lateinit var gosNumber: TextView
    private lateinit var noPhotoTextView: TextView
    private lateinit var back: ImageView
    private lateinit var worksButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_info)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // инициализация БД
        databaseRepository = DatabaseRepository(applicationContext)

        image = findViewById(R.id.background)
        back = findViewById(R.id.backButton)
        worksButton = findViewById(R.id.viewWorks)
        editButton = findViewById(R.id.editActionButton)
        ownerName = findViewById(R.id.getOwnerName)
        carName = findViewById(R.id.getCarName)
        gosNumber = findViewById(R.id.getGosNumber)
        noPhotoTextView = findViewById(R.id.noPhotoTextView)

        // нажата кнопка НАЗАД
        back.setOnClickListener { finish() }

        // нажата кнопка РЕДАКТИРОВАТЬ
        editButton.setOnClickListener {
            Intent(this, EditCarActivity::class.java).apply {
                putExtra(Constants.POSITION_CAR_IN_DB, carId)
                startActivityForResult(this, 1)
            }
        }

        databaseRepository.mainScope().launch {
            getIntentData(intent)
            fillPage()
        }

        // нажата кнопка РАБОТЫ
        worksButton.setOnClickListener {
            Intent(this, WorksActivity::class.java).apply {
                putExtra("modelName", carObject.carModelName)
                putExtra(Constants.POSITION_CAR_IN_DB, carId)
                startActivityForResult(this, 2)
            }
        }

    }

    private fun fillPage() {
        ownerName.text = carObject.carOwnerName
        carName.text = carObject.carModelName
        gosNumber.text = carObject.carGosNumber
        if (carObject.carImage == null) {
            image.setImageResource(R.drawable.ic_background_view)
        } else {
            image.setImageURI(carObject.carImage?.toUri())
            noPhotoTextView.visibility = View.INVISIBLE
        }
    }

    private suspend fun getIntentData(intent: Intent) {
        carId = intent.getLongExtra(Constants.POSITION_CAR_IN_DB, 0)
        carObject = databaseRepository.getCar(carId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                val getCarData = data.getParcelableExtra<CarData>(Constants.OBJECT)
                if (getCarData != null) carObject = getCarData
                carId = data.getLongExtra(Constants.POSITION_CAR_IN_DB, 0)
                databaseRepository.mainScope().launch { carObject = databaseRepository.getCar(carId) }
                fillPage()
            }
        }
    }

}