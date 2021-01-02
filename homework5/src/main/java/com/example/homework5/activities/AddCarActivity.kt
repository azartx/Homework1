package com.example.homework5.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.homework5.R
import com.example.homework5.data.CarData
import com.example.homework5.database.CarsDatabase
import com.example.homework5.database.CarsDatabaseDAO
import java.io.File
import java.util.UUID


class AddCarActivity : AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var ownerName: EditText
    private lateinit var carName: EditText
    private lateinit var gosNumber: EditText
    private lateinit var back: ImageView
    private lateinit var submit: ImageView
    private lateinit var camera: ImageView
    private lateinit var dao: CarsDatabaseDAO

    private var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // инициализация БД
        dao = CarsDatabase.init(this).getCarDatabaseDAO()

        image = findViewById(R.id.background)
        ownerName = findViewById(R.id.workNameEditText)
        carName = findViewById(R.id.carNameEditText)
        gosNumber = findViewById(R.id.gosNumberEditText)
        back = findViewById(R.id.backButton)
        submit = findViewById(R.id.submitButton)
        camera = findViewById(R.id.editActionButton)

        // нажата кнопка КАМЕРЫ
        camera.setOnClickListener {
            photoFile = File(this.filesDir, UUID.randomUUID().toString() + "IMG.jpg")

            val photoUri = FileProvider.getUriForFile(this,
                    "com.example.homework5.fileprovider",
                    photoFile!!)

            val intentGetPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            intentGetPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            getFileWritingPermission(intentGetPhoto, photoUri)

            startActivityForResult(intentGetPhoto, 5)

        }

        // нажата кнопка НАЗАД
        back.setOnClickListener { finish() }

        // нажата кнопка SUBMIT
        submit.setOnClickListener {
            if (ownerName.text.isNotEmpty() && carName.text.isNotEmpty() && gosNumber.text.isNotEmpty()) {

                val car = createCarObject(ownerName, carName, gosNumber)
                dao.addCarToDatabase(car)

                val intent = Intent()
                        .putExtra("objectId", car.id)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun getFileWritingPermission(intentGetPhoto: Intent, photoUri: Uri?) {
        val cameraActivities: List<ResolveInfo> = packageManager.queryIntentActivities(intentGetPhoto,
                PackageManager.MATCH_DEFAULT_ONLY)

        for (cameraActivity in cameraActivities) {
            this.grantUriPermission(cameraActivity.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    private fun createCarObject(ownerName: EditText, carName: EditText, gosNumber: EditText): CarData {
        return CarData(ownerName.text.toString(),
                carName.text.toString(),
                gosNumber.text.toString(),
                photoFile?.path)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        image.setImageURI(photoFile?.path?.toUri())
    }

}