package com.example.homework5

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class AddCarActivity : AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = intent

        image = findViewById(R.id.background)
        val ownerName: EditText = findViewById(R.id.ownerNameEditText)
        val carName: EditText = findViewById(R.id.carNameEditText)
        val gosNumber: EditText = findViewById(R.id.gosNumberEditText)
        val back: ImageView = findViewById(R.id.backButton)
        val submit: ImageView = findViewById(R.id.submitButton)
        val camera: ImageView = findViewById(R.id.createPhotoActionButton)

        // нажатие на кнопку камеры
        camera.setOnClickListener {
            val intentGetPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intentGetPhoto, 1)
        }

        submit.setOnClickListener {
            if (ownerName.text.isNotEmpty() && carName.text.isNotEmpty() && gosNumber.text.isNotEmpty()) {
                /*if (bitmap == null) {
                    bitmap
                }*/

                val car = CarData(ownerName.text.toString(),
                        carName.text.toString(),
                        gosNumber.text.toString(),
                        bitmap)
                intent.putExtra("add", car)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            val photo = data?.extras?.get("data") as Bitmap?
            if (photo != null) {
                    bitmap = Bitmap.createBitmap(photo)
                }
            image.setImageBitmap(photo)
        }
    }


}