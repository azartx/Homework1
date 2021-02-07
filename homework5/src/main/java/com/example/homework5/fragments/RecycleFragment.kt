package com.example.homework5.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.R
import com.example.homework5.adapters.CarAdapter
import com.example.homework5.data.CarData
import com.example.homework5.data.staticData.Constants
import com.example.homework5.data.staticData.Constants.Companion.CAR_ADD_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.CAR_EDIT_FRAGMENT
import com.example.homework5.data.staticData.Constants.Companion.CAR_INFO_FRAGMENT
import com.example.homework5.database.DatabaseRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class RecycleFragment() : Fragment(R.layout.fragment_recycle) {

    private lateinit var localAdapter: CarAdapter
    private lateinit var databaseRepository: DatabaseRepository
    private lateinit var onEditButtonClick: CarAdapter.OnCarClickListener
    private lateinit var logoTextView: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var addActionButton: FloatingActionButton
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recycler = view.findViewById(R.id.recyclerView)
        addActionButton = view.findViewById(R.id.addNewCar)
        logoTextView = view.findViewById(R.id.listIsEmptyTextView)
        //searchView = view.findViewById(R.id.search)
        toolbar = view.findViewById(R.id.toolbar)

        // инициализация БД
        databaseRepository = DatabaseRepository(view.context)

        //  добавление новой машины
        addActionButton.setOnClickListener {
            (activity as ChangeFragmentListener).onChangeFragment(CAR_ADD_FRAGMENT, null)
        }

        // редактирование и информация о машине
        onEditButtonClick = object : CarAdapter.OnCarClickListener {
            override fun onCarClick(carData: CarData, position: Int, flag: Int) {
                when (flag) {
                    1 -> {
                        Bundle().apply {
                            putLong(Constants.POSITION_CAR_IN_DB, carData.id ?: 0)
                            putInt("recycleFragment", 1)
                            (activity as ChangeFragmentListener).onChangeFragment(CAR_EDIT_FRAGMENT, this)
                        }
                    }
                    2 -> {
                        Bundle().apply {
                            putLong(Constants.POSITION_CAR_IN_DB, carData.id ?: 0)
                            (activity as ChangeFragmentListener).onChangeFragment(CAR_INFO_FRAGMENT, this)
                        }
                    }
                }
            }
        }

        // настройка ресайклера и адаптера
        localAdapter = CarAdapter(view.context, ArrayList(), onEditButtonClick)
        recycler.apply {
            layoutManager = LinearLayoutManager(view.context,
                    RecyclerView.VERTICAL, false)
            adapter = localAdapter
        }
        checkDataBase()
/*
        setSearchViewSettings()
*/
    }

    private fun checkDataBase() {
        databaseRepository.mainScope().launch {
            val carList = databaseRepository.getCarsList()
            if (carList.isNotEmpty()) {
                localAdapter.apply {
                    cars = carList as ArrayList<CarData>
                    carsCopy = carList
                    sortByCarBrand()
                    notifyDataSetChanged() // почему то без этого не обновляет адекватно после применения асинхрона
                }
                visibilityForLogoTextView()
            }
        }
    }

    private fun visibilityForLogoTextView() {
        if (localAdapter.cars.isNotEmpty()) logoTextView.visibility = View.INVISIBLE
        else logoTextView.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkDataBase()
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(context)
        inflater.inflate(R.menu.menu, menu)

        searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(chars: String): Boolean {
                localAdapter.filter.filter(chars)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
        return true
    }*/

    /*private fun setSearchViewSettings() {
        searchView = toolbar.menu.findItem(R.id.search)?.actionView as SearchView
        searchView.apply {
            imeOptions = EditorInfo.IME_ACTION_DONE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?) = false

                override fun onQueryTextChange(p0: String?): Boolean {
                    localAdapter.filter.filter(p0)
                    return false
                }
            })
        }
    }*/

}
