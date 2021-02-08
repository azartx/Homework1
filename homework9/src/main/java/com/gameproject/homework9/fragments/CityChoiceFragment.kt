package com.gameproject.homework9.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameproject.homework9.OnChangeFragmentListener
import com.gameproject.homework9.R
import com.gameproject.homework9.database.Cities
import com.gameproject.homework9.databinding.FragmentCityChoiceBinding
import com.gameproject.homework9.databinding.ItemDialogBinding
import com.gameproject.homework9.utils.Constants.WEATHER_FRAGMENT
import com.gameproject.homework9.utils.Constants.actualCity
import com.gameproject.homework9.viewModels.WeatherViewModel

class CityChoiceFragment : Fragment(R.layout.fragment_city_choice) {

    private lateinit var binding: FragmentCityChoiceBinding
    private lateinit var localAdapter: CitiesAdapter
    private lateinit var onCityClickListener: CitiesAdapter.OnCityClickListener
    private var checkedView: ImageView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityChoiceBinding.bind(view)

        onCityClickListener = object : CitiesAdapter.OnCityClickListener {
            override fun onCityClick(city: Cities, viewNeedToCheck: ImageView) {

                actualCity = city.city

                /*ViewModelProvider(this@CityChoiceFragment).get(WeatherViewModel::class.java)
                        .also { viewModel ->
                            viewModel.getOldCityLiveData.observe(viewLifecycleOwner, { oldCity ->
                                oldCity.flag = false
                                city.flag = true
                                viewModel.updateNewCityFlag(oldCity)
                                viewModel.updateNewCityFlag(city)

                            viewModel.getCityWithTrueFlag()
                        }})*/
                checkedView?.visibility = View.INVISIBLE
                checkedView = viewNeedToCheck
            }
        }

        localAdapter = CitiesAdapter(onCityClickListener)
        binding.recycler.apply {
            adapter = localAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.apply {
            addCityFAButton.setOnClickListener { getCityName(view) }
            backButton.setOnClickListener {
                (activity as OnChangeFragmentListener).onFragmentChange(WEATHER_FRAGMENT, null)
            }
        }


    }

    private fun getCityName(view: View) {
        val itemDialogBinding: ItemDialogBinding = ItemDialogBinding.inflate(LayoutInflater.from(view.context))
        AlertDialog.Builder(view.context).apply {
            setView(itemDialogBinding.root)
            setTitle(getString(R.string.enter_city_name))
            setPositiveButton(getString(R.string.ok_dialog_button)) { _, _ ->
                if (itemDialogBinding.cityNameEditText.text.toString().isNotEmpty()) {
                    ViewModelProvider(this@CityChoiceFragment).get(WeatherViewModel::class.java)
                            .also {
                                it.citiesLaveData.observe(viewLifecycleOwner, { city -> localAdapter.addCity(city) })
                                it.addCityIntoDb(view.context, itemDialogBinding.cityNameEditText.text.toString())
                            }
                }
            }
            setNegativeButton(getString(R.string.cancle_dialog_button)) { dialogInterface, _ -> dialogInterface.cancel() }
            setCancelable(false)
            create()
            show()
        }
    }

}