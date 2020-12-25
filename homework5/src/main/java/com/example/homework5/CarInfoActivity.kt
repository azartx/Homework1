package com.example.homework5

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CarInfoActivity : AppCompatActivity() {

    private var carObject: CarData? = null
    private var carPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_info)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = intent

        val image: ImageView = findViewById(R.id.background)
        val back: ImageView = findViewById(R.id.backButton)
        val worksButton: FloatingActionButton = findViewById(R.id.viewWorks)
        val editButton: FloatingActionButton = findViewById(R.id.editActionButton)
        val ownerName: TextView = findViewById(R.id.getOwnerName)
        val carName: TextView = findViewById(R.id.getCarName)
        val gosNumber: TextView = findViewById(R.id.getGosNumber)

        // нажата кнопка НАЗАД
        back.setOnClickListener { finish() }

        getIntentData(intent)

        fillPage(ownerName, carName, gosNumber, image)

    }

    private fun fillPage(ownerName: TextView, carName: TextView, gosNumber: TextView, image: ImageView) {
        ownerName.text = carObject?.carOwnerName
        carName.text = carObject?.carModelName
        gosNumber.text = carObject?.carGosNumber
        if (carObject?.carImage == null) {
            image.setImageResource(R.drawable.ic_background_view)
        } else {
            image.setImageBitmap(carObject?.carImage)
            findViewById<TextView>(R.id.noPhotoTextView).visibility = View.INVISIBLE

        }
    }

    private fun getIntentData(intent: Intent) {
        carObject = intent.getParcelableExtra("object")
        carPosition = intent.getIntExtra("position", 0)
    }


}