package com.example.homework5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework5.R
import com.example.homework5.data.WorkData

class WorkAdapter(context: Context,
                  var works: ArrayList<WorkData>,
                  private val onWorkClickListener: OnWorkClickListener) :
        RecyclerView.Adapter<WorkAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface OnWorkClickListener {
        fun onWorkClick(workData: WorkData, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.item_work_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workData: WorkData = works[position]

        holder.bind(workData, holder, onWorkClickListener)
    }

    override fun getItemCount(): Int {
        return works.size
    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val typeOfWork: TextView = view.findViewById(R.id.typeOfWork)
        private val time: TextView = view.findViewById(R.id.timeTextView)
        private val progress: TextView = view.findViewById(R.id.progressTextView)
        private val coast: TextView = view.findViewById(R.id.coastTextView)
        private val image: ImageView = view.findViewById(R.id.workImage)
        private val parent: CardView = view.findViewById(R.id.parent)

        fun bind(workData: WorkData, holder: ViewHolder, onWorkClickListener: OnWorkClickListener) {

            holder.image.setColorFilter(itemView.resources.getColor(workData.color, itemView.context.theme))
            holder.typeOfWork.text = workData.workName
            holder.time.text = workData.time
            holder.progress.text = workData.progress
            holder.coast.text = workData.coast

            // редактирование работы
            holder.parent.setOnClickListener {
                onWorkClickListener.onWorkClick(workData, adapterPosition)
            }
        }

    }

}
