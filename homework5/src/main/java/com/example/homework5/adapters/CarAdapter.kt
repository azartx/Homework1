package com.example.homework5.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework5.R
import com.example.homework5.data.CarData

class CarAdapter(context: Context,
                 var cars: ArrayList<CarData>,
                 private val onCarClickListener: OnCarClickListener) :
        RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface OnCarClickListener {
        fun onCarClick(carData: CarData, position: Int, flag: Int)
    }

    fun add(carData: CarData) {
        cars.add(carData)
        notifyItemChanged(cars.indexOf(carData))
    }

    fun edit(carData: CarData, position: Int) {
        cars[position] = carData
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        cars.removeAt(position)
        notifyDataSetChanged()
    }

    private fun selector(p: CarData): String = p.carModelName

    fun sortByCarBrand() {
        cars.sortBy { selector(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.item_car_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carData: CarData = cars[position]

        holder.bind(carData, holder, onCarClickListener)
    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.background)
        private val carOwnerName: TextView = view.findViewById(R.id.driverName)
        private val carModelName: TextView = view.findViewById(R.id.carName)
        private val carGosNumber: TextView = view.findViewById(R.id.carNumber)
        private val parent: CardView = view.findViewById(R.id.parent)
        private val carEditButton: ImageView = view.findViewById(R.id.editCarButton)
        private val cameraNoPhoto: ImageView = view.findViewById(R.id.cameraNoPhoto)

        fun bind(carData: CarData, holder: ViewHolder, onCarClickListener: OnCarClickListener) {
            if (carData.carImage == null) {
                holder.image.setImageResource(R.drawable.ic_background_view)
            } else {
                //holder.image.setImageURI(carData.carImage.toUri())
                Glide.with(itemView).load(carData.carImage).into(image)
                cameraNoPhoto.visibility = View.INVISIBLE
            }

            holder.carOwnerName.text = carData.carOwnerName
            holder.carModelName.text = carData.carModelName
            holder.carGosNumber.text = carData.carGosNumber

            // редактирование машины
            holder.carEditButton.setOnClickListener {
                onCarClickListener.onCarClick(carData, adapterPosition, 1)
            }

            // информация о машине
            holder.parent.setOnClickListener {
                onCarClickListener.onCarClick(carData, adapterPosition, 2)
            }

        }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

}