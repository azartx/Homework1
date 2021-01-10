package com.gameproject.homework6_2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerViewAdapter(context: Context,
                          var imageList: ArrayList<ImageObject>,
                          private val onImageClickListener: OnImageClickListener) :
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface OnImageClickListener {
        fun onImageClick(imageObject: ImageObject, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.item_images_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageObject: ImageObject = imageList[position]

        holder.bind(imageObject, holder, onImageClickListener)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        private val activityScope = CoroutineScope(Dispatchers.Main + Job())

        fun bind(imageObject: ImageObject, holder: ViewHolder, onImageClickListener: OnImageClickListener) {

            activityScope.launch {
                val imageFromGlide = withContext(Dispatchers.IO) {
                    Glide.with(itemView).load(imageObject.imageUri)
                            .error(R.drawable.glide_error)
                }
                imageFromGlide.into(thumbnail)
            }

            holder.thumbnail.setOnClickListener {
                onImageClickListener.onImageClick(imageObject, adapterPosition)
            }
        }
    }

}

