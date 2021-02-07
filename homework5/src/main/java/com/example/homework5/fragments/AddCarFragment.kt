package com.example.homework5.fragments

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
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.homework5.R
import com.example.homework5.data.CarData
import com.example.homework5.database.DatabaseRepository
import java.io.File
import java.util.*

class AddCarFragment : Fragment(R.layout.fragment_add_car) {

    private lateinit var image: ImageView
    private lateinit var ownerName: EditText
    private lateinit var carName: EditText
    private lateinit var gosNumber: EditText
    private lateinit var back: ImageView
    private lateinit var submit: ImageView
    private lateinit var camera: ImageView
    private lateinit var databaseRepository: DatabaseRepository

    private var photoFile: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// инициализация БД
        databaseRepository = DatabaseRepository(view.context)

        image = view.findViewById(R.id.background)
        ownerName = view.findViewById(R.id.workNameEditText)
        carName = view.findViewById(R.id.carNameEditText)
        gosNumber = view.findViewById(R.id.gosNumberEditText)
        back = view.findViewById(R.id.backButton)
        submit = view.findViewById(R.id.submitButton)
        camera = view.findViewById(R.id.editActionButton)

        // нажата кнопка КАМЕРЫ
        camera.setOnClickListener {
            photoFile = File(it.context.filesDir, UUID.randomUUID().toString() + "IMG.jpg")

            val photoUri = FileProvider.getUriForFile(view.context,
                    "com.example.homework5.fileprovider",
                    photoFile!!)

            val intentGetPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            intentGetPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            getFileWritingPermission(intentGetPhoto, photoUri)

            startActivityForResult(intentGetPhoto, 5)

        }

        // нажата кнопка НАЗАД
        back.setOnClickListener { this.activity?.onBackPressed() }

        // нажата кнопка SUBMIT
        submit.setOnClickListener {
            if (ownerName.text.isNotEmpty() && carName.text.isNotEmpty() && gosNumber.text.isNotEmpty()) {
                createCarObject().apply {
                    databaseRepository.addCar(this)
                    /*val intent = Intent()
                            .putExtra("objectId", this.id)
                    setResult(AppCompatActivity.RESULT_OK, intent)
                    finish()*/

                    parentFragmentManager.beginTransaction()
                            .replace(R.id.rootFragment, RecycleFragment::class.java, null)
                            .commit()
                }
            } else {
                Toast.makeText(this.context, getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show()
            }
        }

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getFileWritingPermission(intentGetPhoto: Intent, photoUri: Uri?) {
        val cameraActivities: List<ResolveInfo> = view?.context?.packageManager?.queryIntentActivities(intentGetPhoto,
                PackageManager.MATCH_DEFAULT_ONLY) as List<ResolveInfo>

        for (cameraActivity in cameraActivities) {
            view?.context?.grantUriPermission(cameraActivity.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    private fun createCarObject() =
            CarData(ownerName.text.toString(),
                    carName.text.toString(),
                    gosNumber.text.toString(),
                    photoFile?.path
            )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        image.setImageURI(photoFile?.path?.toUri())
    }

}