package com.example.homework5.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.example.homework5.R
import com.example.homework5.data.CarData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.data.staticData.Constants.Companion.CAR_INFO_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.CAR_RECYCLE_FRAGMENT
import com.example.homework5.database.DatabaseRepository
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class EditCarFragment : Fragment(R.layout.fragment_edit_car) {

    private var carId: Long = 0
    private lateinit var toolbar: Toolbar
    private lateinit var databaseRepository: DatabaseRepository
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // инициализация БД
        databaseRepository = DatabaseRepository(view.context)

        ownerName = view.findViewById(R.id.workNameEditText)
        carName = view.findViewById(R.id.carNameEditText)
        gosNumber = view.findViewById(R.id.gosNumberEditText)
        back = view.findViewById(R.id.backButton)
        submit = view.findViewById(R.id.submitButton)
        camera = view.findViewById(R.id.editActionButton)
        image = view.findViewById(R.id.background)
        noPhotoTextView = view.findViewById(R.id.noPhotoTextView)

        getIntentExtras()

        Log.i("FFFF", arguments?.getInt("carInfoFragment").toString())
        Log.i("FFFF", "arguments.getLong(Constants.POSITION_CAR_IN_DB).toString()")

        createFileAndUri(view.context)

        // нажата кнопка НАЗАД
        back.setOnClickListener { this.activity?.onBackPressed() }

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
        val cameraActivities: List<ResolveInfo> = view?.context?.packageManager?.queryIntentActivities(intentGetPhoto,
                PackageManager.MATCH_DEFAULT_ONLY) as List<ResolveInfo>

        for (cameraActivity in cameraActivities) {
            context?.grantUriPermission(cameraActivity.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    private fun createFileAndUri(context: Context) {
        photoFile = File(context.filesDir, UUID.randomUUID().toString() + "IMG.jpg")

        photoUri = FileProvider.getUriForFile(context,
                "com.example.homework5.fileprovider",
                photoFile!!)
    }

    private fun submitListenerBody(ownerName: EditText,
                                   carName: EditText,
                                   gosNumber: EditText) {
        if (ownerName.text.isNotEmpty() && carName.text.isNotEmpty() && gosNumber.text.isNotEmpty()) {
            val car = fillCarObject()

            databaseRepository.updateCar(car)

            if (arguments?.containsKey("recycleFragment") == true) {
                (activity as ChangeFragmentListener).onChangeFragment(CAR_RECYCLE_FRAGMENT, null)
            } else {
                Bundle().apply {
                    putLong(Constants.POSITION_CAR_IN_DB, carId)
                    (activity as ChangeFragmentListener).onChangeFragment(CAR_INFO_FRAGMENT, this)
                }
            }
        } else {
            Toast.makeText(context, getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show()
        }
    }

    private fun fillCarObject() =
            CarData(ownerName.text.toString(),
                    carName.text.toString(),
                    gosNumber.text.toString(),
                    photoUri.toString()
            ).also { it.id = carId }


    private fun getIntentExtras() {
        carId = arguments?.getLong(Constants.POSITION_CAR_IN_DB, 0) ?: -1
        if (carId != -1L) {
            databaseRepository.mainScope().launch {
                carObject = databaseRepository.getCar(carId)
                fillPage()
            }
        } else {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
        }
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