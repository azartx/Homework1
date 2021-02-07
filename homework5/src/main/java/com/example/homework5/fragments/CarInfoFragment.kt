package com.example.homework5.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.homework5.R
import com.example.homework5.data.CarData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.data.staticData.Constants.Companion.CAR_EDIT_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.WORK_RECYCLE_FRAGMENT
import com.example.homework5.database.DatabaseRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class CarInfoFragment : Fragment(R.layout.fragment_car_info) {

    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var carObject: CarData
    private var carId: Long = 0
    private lateinit var image: ImageView
    private lateinit var ownerName: TextView
    private lateinit var carName: TextView
    private lateinit var gosNumber: TextView
    private lateinit var noPhotoTextView: TextView
    private lateinit var back: ImageView
    private lateinit var worksButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // инициализация БД
        databaseRepository = DatabaseRepository(view.context)

        image = view.findViewById(R.id.background)
        back = view.findViewById(R.id.backButton)
        worksButton = view.findViewById(R.id.viewWorks)
        editButton = view.findViewById(R.id.editActionButton)
        ownerName = view.findViewById(R.id.getOwnerName)
        carName = view.findViewById(R.id.getCarName)
        gosNumber = view.findViewById(R.id.getGosNumber)
        noPhotoTextView = view.findViewById(R.id.noPhotoTextView)

        // нажата кнопка НАЗАД
        back.setOnClickListener { this.activity?.onBackPressed() }

        // нажата кнопка РЕДАКТИРОВАТЬ
        editButton.setOnClickListener {
            Bundle().apply {
                putLong(Constants.POSITION_CAR_IN_DB, carId)
                putInt("carInfoFragment", 2)
                (activity as ChangeFragmentListener).onChangeFragment(CAR_EDIT_FRAGMENT, this)
            }
        }

        // нажата кнопка РАБОТЫ
        worksButton.setOnClickListener {
            Bundle().apply {
                putString("modelName", carObject.carModelName)
                putLong(Constants.POSITION_CAR_IN_DB, carId)
                (activity as ChangeFragmentListener).onChangeFragment(WORK_RECYCLE_FRAGMENT, this)
            }
        }

        databaseRepository.mainScope().launch {
            getIntentData()
            fillPage()
        }
    }

    private fun fillPage() {
        ownerName.text = carObject.carOwnerName
        carName.text = carObject.carModelName
        gosNumber.text = carObject.carGosNumber
        if (carObject.carImage == null) {
            image.setImageResource(R.drawable.ic_background_view)
        } else {
            image.setImageURI(carObject.carImage?.toUri())
            noPhotoTextView.visibility = View.INVISIBLE
        }
    }

    private suspend fun getIntentData() {
        carId = arguments?.getLong(Constants.POSITION_CAR_IN_DB, 0) ?: 0
        carObject = databaseRepository.getCar(carId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            if (data != null) {
                val getCarData = data.getParcelableExtra<CarData>(Constants.OBJECT)
                if (getCarData != null) carObject = getCarData
                carId = data.getLongExtra(Constants.POSITION_CAR_IN_DB, 0)
                databaseRepository.mainScope().launch { carObject = databaseRepository.getCar(carId) }
                fillPage()
            }
        }
    }


}