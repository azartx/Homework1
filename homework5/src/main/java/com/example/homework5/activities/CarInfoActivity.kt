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
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CarInfoActivity : AppCompatActivity() {

    private var carObject: CarData? = null
    private var carPosition: Int = 0
    private var checkInputActivity = false
    private lateinit var image: ImageView
    private lateinit var ownerName: TextView
    private lateinit var carName: TextView
    private lateinit var gosNumber: TextView

    companion object {
        const val OBJECT = "editCar"
        const val POSITION = "editPosition"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_info)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = intent

        image = findViewById(R.id.background)
        val back: ImageView = findViewById(R.id.backButton)
        val worksButton: FloatingActionButton = findViewById(R.id.viewWorks)
        val editButton: FloatingActionButton = findViewById(R.id.editActionButton)
        ownerName = findViewById(R.id.getOwnerName)
        carName = findViewById(R.id.getCarName)
        gosNumber = findViewById(R.id.getGosNumber)

        // нажата кнопка НАЗАД
        back.setOnClickListener {
            if (checkInputActivity) {
                intent.putExtra(OBJECT, carObject)
                intent.putExtra(POSITION, carObject)
                setResult(RESULT_OK, intent)
                finish()
            }
            finish()
        }

        // нажата кнопка РЕДАКТИРОВАТЬ
        editButton.setOnClickListener {
            val editIntent = Intent(this, EditCarActivity::class.java)
            editIntent.putExtra(OBJECT, carObject)
            editIntent.putExtra(POSITION, carPosition)
            startActivityForResult(editIntent, 1)
        }

        if (!checkInputActivity) getIntentData(intent)

        // нажата кнопка РАБОТЫ
        worksButton.setOnClickListener {
            val worksIntent = Intent(this, WorksActivity::class.java)
            worksIntent.putExtra(OBJECT, carObject)
            worksIntent.putExtra(POSITION, carPosition)
            startActivityForResult(worksIntent, 2)
        }

        fillPage()

    }

    private fun fillPage() {
        ownerName.text = carObject?.carOwnerName
        carName.text = carObject?.carModelName
        gosNumber.text = carObject?.carGosNumber
        if (carObject?.carImage == null) {
            image.setImageResource(R.drawable.ic_background_view)
        } else {
            image.setImageURI(carObject!!.carImage!!.toUri())
            findViewById<TextView>(R.id.noPhotoTextView).visibility = View.INVISIBLE

        }
    }

    private fun getIntentData(intent: Intent) {
        carObject = intent.getParcelableExtra(OBJECT)
        carPosition = intent.getIntExtra(POSITION, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                carObject = data.getParcelableExtra(OBJECT)
                carPosition = data.getIntExtra(POSITION, 0)
                checkInputActivity = true
                fillPage()
            }
        }

    }


}