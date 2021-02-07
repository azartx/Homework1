package com.example.homework5.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.R
import com.example.homework5.data.staticData.Constants.Companion.WORK_ADD_FRAGMENT
import com.example.homework5.adapters.WorkAdapter
import com.example.homework5.data.WorkData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.data.staticData.Constants.Companion.WORK_EDIT_FRAGMENT
import com.example.homework5.database.DatabaseRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class WorkRecycleFragment : Fragment(R.layout.fragment_work_recycle) {

    private lateinit var logoTextView: TextView
    private var parentCar: String? = null
    private var carId: Long = 0
    private lateinit var localAdapter: WorkAdapter
    private lateinit var editWorkListener: WorkAdapter.OnWorkClickListener
    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var recycler: RecyclerView
    private lateinit var addWorkActionButton: FloatingActionButton
    private lateinit var carNameInToolbar: TextView
    private lateinit var backButton: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carNameInToolbar = view.findViewById(R.id.carNameInToolbar)
        recycler = view.findViewById(R.id.recyclerView)
        addWorkActionButton = view.findViewById(R.id.addNewWork)
        backButton = view.findViewById(R.id.backButton)
        logoTextView = view.findViewById(R.id.worksIsEmptyTextView)

        getIntentData(carNameInToolbar)

        // инициализация БД
        databaseRepository = DatabaseRepository(view.context)

        // нажата кнопка ДОБАВИТЬ РАБОТУ
        addWorkActionButton.setOnClickListener {
            Bundle().apply {
                putString(Constants.PARENT_CAR, parentCar)
                (activity as ChangeFragmentListener).onChangeFragment(WORK_ADD_FRAGMENT, this)
            }
        }

        // нажатие на работу
        editWorkListener = object : WorkAdapter.OnWorkClickListener {
            override fun onWorkClick(workData: WorkData, position: Int) {
                Bundle().apply {
                    putLong(Constants.POSITION_CAR_IN_DB, workData.id ?: 0)
                    (activity as ChangeFragmentListener).onChangeFragment(WORK_EDIT_FRAGMENT, this)
                }
            }
        }

        // нажата кнопка назад
        backButton.setOnClickListener { this.activity?.onBackPressed() }

        // настройка ресайклера и адаптера
        val localLayoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        localAdapter = WorkAdapter(view.context, ArrayList(), editWorkListener)
        recycler.apply {
            layoutManager = localLayoutManager
            adapter = localAdapter
        }
        checkDataBase()

    }

    private fun visibilityForLogoTextView() {
        if (localAdapter.works.isNotEmpty()) logoTextView.visibility = View.INVISIBLE
        else logoTextView.visibility = View.VISIBLE
    }

    private fun checkDataBase() {
        databaseRepository.mainScope().launch {
            val workList = databaseRepository.getParentWorks(parentCar)
            if (workList.isNotEmpty()) {
                localAdapter.apply {
                    works = workList as ArrayList<WorkData>
                    visibilityForLogoTextView()
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun getIntentData(carNameInToolbar: TextView) {
        carId = arguments?.getLong(Constants.POSITION_CAR_IN_DB, 0) ?: 0
        parentCar = arguments?.getString("modelName", "ERROR NAME")

        carNameInToolbar.text = parentCar ?: getString(R.string.car_works)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkDataBase()
    }

}