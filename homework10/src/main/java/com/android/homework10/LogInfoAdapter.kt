package com.android.homework10

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.homework10.databinding.ItemRecyclerBoxBinding

class LogInfoAdapter : RecyclerView.Adapter<LogInfoAdapter.LogViewHolder>() {
    var logList: List<LogData> = emptyList()

    fun updateList(list: List<LogData>) {
        logList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LogViewHolder(
            ItemRecyclerBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        holder.bind(logList[position])
    }

    override fun getItemCount() = logList.size

    class LogViewHolder(private val binding: ItemRecyclerBoxBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(logPool: LogData) {
            binding.logPool.text = logPool.eventName
            binding.timePool.text = logPool.dataTime

            binding.root.setOnClickListener {
                Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "abc@gmail.com", null)).apply {
                    putExtra(Intent.EXTRA_SUBJECT, logPool.eventName.plus(" | ${logPool.dataTime}"))
                    itemView.context.startActivity(Intent.createChooser(this, null))
                }
            }
        }
    }

}
