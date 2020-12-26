package com.example.homework5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.R
import com.example.homework5.data.WorkData

class WorkAdapter(context: Context,
                  private val works: ArrayList<WorkData>,
                  private val onWorkClickListener: WorkAdapter.OnWorkClickListener) :
        RecyclerView.Adapter<WorkAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface OnWorkClickListener {
        fun onWorkClick(workData: WorkData, position: Int)
    }

    fun add(workData: WorkData) {
        works.add(workData)
        notifyItemChanged(works.indexOf(workData))
    }

    fun edit(workData: WorkData, position: Int) {
        works[position] = workData
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        works.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkAdapter.ViewHolder {
        val view: View = inflater.inflate(R.layout.item_work_list, parent, false)

        return WorkAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkAdapter.ViewHolder, position: Int) {
        val workData: WorkData = works[position]

        holder.bind(workData, holder, onWorkClickListener)
    }

    override fun getItemCount(): Int {
        return works.size
    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val typeOfWork: TextView = view.findViewById(R.id.typeOfWorkTextView)
        private val time: TextView = view.findViewById(R.id.timeTextView)
        private val progress: TextView = view.findViewById(R.id.progressTextView)
        private val coast: TextView = view.findViewById(R.id.coastTextView)
        private val image: ImageView = view.findViewById(R.id.workImage)

        fun bind(workData: WorkData, holder: WorkAdapter.ViewHolder, onWorkClickListener: WorkAdapter.OnWorkClickListener) {
            /*if (carData.carImage == null) {
                holder.image.setImageResource(R.drawable.ic_background_view)
            } else {
                holder.image.setImageBitmap(carData.carImage)
                cameraNoPhoto.visibility = View.INVISIBLE
            }*/

            holder.typeOfWork.text = workData.typeOfWork
            holder.time.text = workData.time
            holder.progress.text = workData.progress
            holder.coast.text = workData.coast

            /*// редактирование машины
            holder.carEditButton.setOnClickListener {
                onCarClickListener.onCarClick(carData, adapterPosition, 1)
            }

            // информация о машине
            holder.parent.setOnClickListener {
                onCarClickListener.onCarClick(carData, adapterPosition, 2)
            }*/

        }

    }

}
