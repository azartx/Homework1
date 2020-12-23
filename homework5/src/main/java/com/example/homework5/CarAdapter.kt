package com.example.homework5

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(private val context: Context,
                  val cars: ArrayList<CarData>) :
        RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    fun add(carData: CarData) {
        cars.add(carData)
        notifyItemChanged(cars.indexOf(carData))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.item_car_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carData: CarData = cars[position]

        holder.bind(carData, holder)
    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.background)
        private val carOwnerName: TextView = view.findViewById(R.id.driverName)
        private val carModelName: TextView = view.findViewById(R.id.carName)
        private val carGosNumber: TextView = view.findViewById(R.id.carNumber)
        private val parent: ConstraintLayout = view.findViewById(R.id.parent)

        fun bind(carData: CarData, holder: ViewHolder) {
            holder.image.setImageBitmap(carData.carImage)
            holder.carOwnerName.text = carData.carOwnerName
            holder.carModelName.text = carData.carModelName
            holder.carGosNumber.text = carData.carGosNumber

            //holder.parent.setOnClickListener()

        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }


}