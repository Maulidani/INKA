package com.test.inka.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.test.inka.R
import com.test.inka.databinding.ItemVaccineRequestBinding
import com.test.inka.model.DataResult

class RequestVaccineAdapter(
    private val vaccineList: ArrayList<DataResult>,

    ) :
    RecyclerView.Adapter<RequestVaccineAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemVaccineRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {

            binding.tvChildname.text = dataList.name
            binding.tvName.text = dataList.name
            binding.tvAge.text = dataList.age_month
            binding.tvImmunization.text = dataList.vaccine_name
            binding.tvDate.text = dataList.vaccine_date

            binding.cardVaccineList.setOnClickListener {
                Toast.makeText(it.context, dataList.status, Toast.LENGTH_SHORT).show()
            }

            if (dataList.expendable) {
                binding.parentDetails.visibility = View.VISIBLE
                binding.icDetails.setImageResource(R.drawable.ic_up)
            } else {
                binding.parentDetails.visibility = View.GONE
                binding.icDetails.setImageResource(R.drawable.ic_down)
            }

            binding.parentNameList.setOnClickListener {
                dataList.expendable = !dataList.expendable
                notifyDataSetChanged()
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemVaccineRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(vaccineList[position])
    }

    override fun getItemCount(): Int = vaccineList.size
}