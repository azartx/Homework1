package com.example.homework5.activities

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
import java.io.File
import java.util.UUID

class EditCarActivity : AppCompatActivity() {

    private lateinit var image: ImageView
    private var photoFile: File? = null
    private var photoUri: Uri? = null

    private var carObject: CarData? = null
    private var carPosition: Int = 0

    companion object {
        const val OBJECT = "editCar"
        const val POSITION = "editPosition"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val ownerName: EditText = findViewById(R.id.workNameEditText)
        val carName: EditText = findViewById(R.id.carNameEditText)
        val gosNumber: EditText = findViewById(R.id.gosNumberEditText)
        val back: ImageView = findViewById(R.id.backButton)
        val submit: ImageView = findViewById(R.id.submitButton)
        val camera: ImageView = findViewById(R.id.editActionButton)
        image = findViewById(R.id.background)

        val intent = intent

        getIntentExtras(intent)

        if (carObject != null) fillPage(ownerName, carName, gosNumber, image)

        createFileAndUri()

        // нажата кнопка НАЗАД
        back.setOnClickListener { finish() }

        // нажата кнопка КАМЕРА
        camera.setOnClickListener {
            val intentGetPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            packageManager.resolveActivity(intentGetPhoto,
                    PackageManager.MATCH_DEFAULT_ONLY)

            intentGetPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            val cameraActivities: List<ResolveInfo> = packageManager.queryIntentActivities(intentGetPhoto,
                    PackageManager.MATCH_DEFAULT_ONLY)

            for (cameraActivity in cameraActivities) {
                this.grantUriPermission(cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            startActivityForResult(intentGetPhoto, 1)
        }

        // нажата кнопка SUBMIT
        submit.setOnClickListener {
            submitListenerBody(ownerName, carName, gosNumber, intent)
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
                                   gosNumber: EditText,
                                   intent: Intent) {
        if (ownerName.text.isNotEmpty() && carName.text.isNotEmpty() && gosNumber.text.isNotEmpty()) {
            val car = fillCarObject(ownerName, carName, gosNumber)

            intent.putExtra(OBJECT, car)
            intent.putExtra(POSITION, carPosition)
            setResult(RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show()
        }
    }

    private fun fillCarObject(ownerName: EditText, carName: EditText, gosNumber: EditText): CarData {
        return CarData(ownerName.text.toString(),
                carName.text.toString(),
                gosNumber.text.toString(),
                photoFile?.path ?: carObject?.carImage)

    }

    private fun getIntentExtras(intent: Intent) {
        carObject = intent.getParcelableExtra(OBJECT)
        carPosition = intent.getIntExtra(POSITION, 0)
    }

    private fun fillPage(ownerName: EditText, carName: EditText, gosNumber: EditText, image: ImageView) {
        ownerName.setText(carObject?.carOwnerName)
        carName.setText(carObject?.carModelName)
        gosNumber.setText(carObject?.carGosNumber)
        if (carObject?.carImage == null) {
            image.setImageResource(R.drawable.ic_background_view)
        } else {
            image.setImageURI(carObject!!.carImage!!.toUri())
            findViewById<TextView>(R.id.noPhotoTextView).visibility = View.INVISIBLE

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        image.setImageURI(photoFile?.path?.toUri())

        findViewById<TextView>(R.id.noPhotoTextView).visibility = View.INVISIBLE

    }
}


