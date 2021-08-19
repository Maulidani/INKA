package com.test.inka.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.inka.databinding.ItemNotificationBinding
import com.test.inka.model.DataResult

class NotificationAdapter(
    private val notifList: ArrayList<DataResult>
) :
    RecyclerView.Adapter<NotificationAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {

            binding.tvBody.text = dataList.body
            binding.tvUser.text = dataList.user
            binding.tvDate.text = dataList.created_at
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ListViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ListViewHolder, position: Int) {
        holder.bind(notifList[position])
    }

    override fun getItemCount(): Int = notifList.size
}