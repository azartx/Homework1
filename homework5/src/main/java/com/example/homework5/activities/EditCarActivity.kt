package com.example.homework5.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.homework5.R
import com.example.homework5.data.CarData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.database.CarsDatabaseRepository
import java.io.File
import java.util.UUID
import java.util.function.Consumer

class EditCarActivity : AppCompatActivity() {

    private var carId: Long = 0
    private lateinit var toolbar: Toolbar
    private lateinit var carsDatabaseRepository: CarsDatabaseRepository
    private var photoFile: File? = null
    private var photoUri: Uri? = null

    private var carObject: CarData? = null

    private lateinit var image: ImageView
    private lateinit var ownerName: EditText
    private lateinit var carName: EditText
    private lateinit var gosNumber: EditText
    private lateinit var back: ImageView
    private lateinit var submit: ImageView
    private lateinit var camera: ImageView
    private lateinit var noPhotoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // инициализация БД
        carsDatabaseRepository = CarsDatabaseRepository(applicationContext)

        ownerName = findViewById(R.id.workNameEditText)
        carName = findViewById(R.id.carNameEditText)
        gosNumber = findViewById(R.id.gosNumberEditText)
        back = findViewById(R.id.backButton)
        submit = findViewById(R.id.submitButton)
        camera = findViewById(R.id.editActionButton)
        image = findViewById(R.id.background)
        noPhotoTextView = findViewById(R.id.noPhotoTextView)

        getIntentExtras(intent)

        if (carObject != null) fillPage()

        createFileAndUri()

        // нажата кнопка НАЗАД
        back.setOnClickListener { finish() }

        // нажата кнопка КАМЕРА
        camera.setOnClickListener {
            val intentGetPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    .putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            getPermissions(intentGetPhoto)
            startActivityForResult(intentGetPhoto, 1)
        }

        // нажата кнопка SUBMIT
        submit.setOnClickListener {
            submitListenerBody(ownerName, carName, gosNumber)
        }

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getPermissions(intentGetPhoto: Intent) {
        val cameraActivities: List<ResolveInfo> = packageManager.queryIntentActivities(intentGetPhoto,
                PackageManager.MATCH_DEFAULT_ONLY)

        for (cameraActivity in cameraActivities) {
            this.grantUriPermission(cameraActivity.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    private fun createFileAndUri() {
        photoFile = File(this.filesDir, UUID.randomUUID().toString() + "IMG.jpg")

        photoUri = FileProvider.getUriForFile(this,
                "com.example.homework5.fileprovider",
                photoFile!!)
    }

    private fun submitListenerBody(ownerName: EditText,
                                   carName: EditText,
                                   gosNumber: EditText) {
        if (ownerName.text.isNotEmpty() && carName.text.isNotEmpty() && gosNumber.text.isNotEmpty()) {
            val car = fillCarObject()

            carsDatabaseRepository.updateCar(car)

            Intent().apply {
                putExtra(Constants.POSITION_CAR_IN_DB, carId)
                setResult(RESULT_OK, this)
                finish()
            }
        } else {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show()
        }
    }

    private fun fillCarObject() =
            CarData(ownerName.text.toString(),
                    carName.text.toString(),
                    gosNumber.text.toString(),
                    photoUri.toString()
            ).also { it.id = carId }


    private fun getIntentExtras(intent: Intent) {
        carId = intent.getLongExtra(Constants.POSITION_CAR_IN_DB, 0)
        carsDatabaseRepository.getCar(carId).thenAcceptAsync(Consumer<CarData> { carObject = it }, mainExecutor)
    }

    private fun fillPage() {
        ownerName.setText(carObject?.carOwnerName)
        carName.setText(carObject?.carModelName)
        gosNumber.setText(carObject?.carGosNumber)
        if (carObject?.carImage == null) {
            image.setImageResource(R.drawable.ic_background_view)
        } else {
            image.setImageURI(carObject?.carImage?.toUri())
            noPhotoTextView.visibility = View.INVISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        image.setImageURI(photoFile?.path?.toUri())
        noPhotoTextView.visibility = View.INVISIBLE
    }

}


