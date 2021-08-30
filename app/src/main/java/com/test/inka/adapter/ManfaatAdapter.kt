package com.test.inka.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.inka.databinding.ItemManfaatImunisasiBinding
import com.test.inka.databinding.ItemNotificationBinding
import com.test.inka.model.DataResult
import com.test.inka.model.Manfaat

class ManfaatAdapter(private val manfaatList: List<Manfaat>) :
    RecyclerView.Adapter<ManfaatAdapter.ManfaatViewlHoder>() {

    inner class ManfaatViewlHoder(private val binding: ItemManfaatImunisasiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: Manfaat) {

            binding.tvName.text = dataList.nama
            binding.tvAge.text = dataList.usia
            binding.tvTotal.text = dataList.jumlah
            binding.tvDesc.text = dataList.deskripsi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManfaatViewlHoder {
        val binding =
            ItemManfaatImunisasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManfaatViewlHoder(binding)
    }

    override fun onBindViewHolder(holder: ManfaatViewlHoder, position: Int) {
        holder.bind(manfaatList[position])
    }

    override fun getItemCount(): Int = manfaatList.size
}