package com.gameproject.homework9.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gameproject.homework9.R
import com.gameproject.homework9.database.Cities

class CitiesAdapter(val cityList: ArrayList<Cities>) : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CitiesViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_city_choice, parent, false))

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(city = cityList[position], holder)
    }

    override fun getItemCount() = cityList.size


    class CitiesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(city: Cities, holder: RecyclerView.ViewHolder) {

        }
    }

}