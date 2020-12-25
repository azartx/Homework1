package com.example.homework5

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EditCarActivity : AppCompatActivity() {

    private lateinit var image: ImageView
    private var carObject: CarData? = null
    private var carPosition: Int = 0
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val ownerName: EditText = findViewById(R.id.ownerNameEditText)
        val carName: EditText = findViewById(R.id.carNameEditText)
        val gosNumber: EditText = findViewById(R.id.gosNumberEditText)
        val back: ImageView = findViewById(R.id.backButton)
        val submit: ImageView = findViewById(R.id.submitButton)
        val camera: ImageView = findViewById(R.id.editActionButton)
        image = findViewById(R.id.background)

        val intent = intent

        getIntentExtras(intent)

        if (carObject != null) fillPage(ownerName, carName, gosNumber, image)

        // нажата кнопка НАЗАД
        back.setOnClickListener { finish() }

        // нажата кнопка КАМЕРА
        camera.setOnClickListener {
            val intentGetPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intentGetPhoto, 1)
        }

        // нажата кнопка SUBMIT
        submit.setOnClickListener {

            submitListenerBody(ownerName, carName, gosNumber, intent)

        }

    }

    private fun submitListenerBody(ownerName: EditText, carName: EditText, gosNumber: EditText, intent: Intent) {
        if (ownerName.text.isNotEmpty() && carName.text.isNotEmpty() && gosNumber.text.isNotEmpty()) {
            val car = fillCarObject(ownerName, carName, gosNumber)

            intent.putExtra("editCar", car)
            intent.putExtra("editPosition", carPosition)
            setResult(RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show()
        }
    }

    private fun fillCarObject(ownerName: EditText, carName: EditText, gosNumber: EditText): CarData {
        return if (bitmap == null) {
            CarData(ownerName.text.toString(),
                    carName.text.toString(),
                    gosNumber.text.toString(),
                    null)
        } else {
            CarData(ownerName.text.toString(),
                    carName.text.toString(),
                    gosNumber.text.toString(),
                    bitmap)
        }
    }

    private fun getIntentExtras(intent: Intent) {
        carObject = intent.getParcelableExtra("editCar")
        carPosition = intent.getIntExtra("editPosition", 0)
    }

    private fun fillPage(ownerName: EditText, carName: EditText, gosNumber: EditText, image: ImageView) {
        ownerName.setText(carObject?.carOwnerName)
        carName.setText(carObject?.carModelName)
        gosNumber.setText(carObject?.carGosNumber)
        if (carObject?.carImage == null) {
            image.setImageResource(R.drawable.ic_background_view)
        } else {
            image.setImageBitmap(carObject?.carImage)
            findViewById<TextView>(R.id.noPhotoTextView).visibility = View.INVISIBLE

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val photo = data?.extras?.get("data") as Bitmap?
            if (photo != null) {
                bitmap = photo
            }
            image.setImageBitmap(photo)
            findViewById<TextView>(R.id.noPhotoTextView).visibility = View.INVISIBLE
        }
    }


}