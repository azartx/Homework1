package com.gameproject.homework9.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gameproject.homework9.database.Cities
import com.gameproject.homework9.databinding.ItemRecycleLayoutBinding

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {

     var cityList: ArrayList<Cities> = ArrayList()


    fun addCity(cityName: Cities) {
        cityList.add(cityName)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CitiesViewHolder(ItemRecycleLayoutBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount() = cityList.size

    class CitiesViewHolder(private val binding: ItemRecycleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: Cities) {
            binding.cityName.text = city.city
        }
    }

}