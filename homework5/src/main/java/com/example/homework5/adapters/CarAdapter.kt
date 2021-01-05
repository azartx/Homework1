package com.example.homework5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework5.R
import com.example.homework5.data.CarData


class CarAdapter(context: Context,
                 var cars: ArrayList<CarData>,
                 private val onCarClickListener: OnCarClickListener) :
        RecyclerView.Adapter<CarAdapter.ViewHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var carsCopy: ArrayList<CarData> = ArrayList(cars)

    interface OnCarClickListener {
        fun onCarClick(carData: CarData, position: Int, flag: Int)
    }

    /*       // эти три братишки перестали иметь ценность, после того, как мы променяли их на DAO
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
    }*/

    private fun selector(p: CarData): String = p.carModelName

    fun sortByCarBrand() {
        cars.sortBy { selector(it) }
        carsCopy.sortBy { selector(it) }
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

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object: Filter(){
        override fun performFiltering(chars: CharSequence?): FilterResults {
            val filteredList = arrayListOf<CarData>()
            if (chars == null || chars.isEmpty()) {
                filteredList.addAll(carsCopy)
            }else{
                val filterPattern = chars.toString().toLowerCase().trim()
                carsCopy.forEach {
                    if (it.carModelName.toLowerCase().contains(filterPattern)||it.carModelName.toLowerCase().contains(filterPattern)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            cars.clear()
            cars.addAll(p1?.values as ArrayList<CarData>);
            notifyDataSetChanged()
        }
    }

}