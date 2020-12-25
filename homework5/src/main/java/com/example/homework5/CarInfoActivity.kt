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
    private var checkInputActivity = false
    private lateinit var image: ImageView
    private lateinit var ownerName: TextView
    private lateinit var carName: TextView
    private lateinit var gosNumber: TextView

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
                intent.putExtra("editCar", carObject)
                intent.putExtra("editPosition", carObject)
                setResult(RESULT_OK, intent)
                finish()
            }
            finish()

        }

        // нажата кнопка РЕДАКТИРОВАТЬ
        editButton.setOnClickListener {
            val editIntent = Intent(this, EditCarActivity::class.java)
            editIntent.putExtra("editCar", carObject)
            editIntent.putExtra("editPosition", carPosition)
            startActivityForResult(editIntent, 1)
        }

        if (!checkInputActivity) getIntentData(intent)

        fillPage()

    }

    private fun fillPage() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                carObject = data.getParcelableExtra("editCar")
                carPosition = data.getIntExtra("editPosition", 0)
                checkInputActivity = true
                fillPage()
            }
        }

    }


}