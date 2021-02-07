package com.gameproject.homework9.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gameproject.homework9.R
import com.gameproject.homework9.database.Cities
import com.gameproject.homework9.databinding.FragmentCityChoiceBinding
import com.gameproject.homework9.databinding.ItemDialogBinding
import com.gameproject.homework9.viewModels.WeatherViewModel

class CityChoiceFragment : Fragment(R.layout.fragment_city_choice) {

    private lateinit var binding: FragmentCityChoiceBinding
    private lateinit var localAdapter: CitiesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityChoiceBinding.bind(view)

        localAdapter = CitiesAdapter()
        binding.recycler.apply {
            adapter = localAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.addCityFAButton.setOnClickListener {
            getCityName(view)

        }


/*val a = arrayListOf<Cities>()
        a.add(Cities("44Grodno"))
        a.add(Cities("qwe"))
        a.add(Cities("Groasddno"))
        a.add(Cities("Grozxcdno"))
        a.add(Cities("sdsd"))

        localAdapter.addCity(a)*/


    }

    private fun getCityName(view: View) {
        val itemDialogBinding: ItemDialogBinding = ItemDialogBinding.inflate(LayoutInflater.from(view.context))
        AlertDialog.Builder(view.context).apply {
            setView(itemDialogBinding.root)
            setTitle("Title")
            setMessage("Message")
            setPositiveButton("Ok") { _, _ ->
                if (true) {

                    ViewModelProvider(this@CityChoiceFragment).get(WeatherViewModel::class.java)
                            .also {
                                it.citiesLaveData.observe(viewLifecycleOwner, { city ->
                                    localAdapter.addCity(city)
                                    Log.i("FFFF", "fghdfgh")
                                })
                                it.addCityIntoDb(view.context, itemDialogBinding.cityNameEditText.text.toString())
                            }
                }
            }
            setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.cancel() }
            setCancelable(false)
            create()
            show()
        }
    }


}