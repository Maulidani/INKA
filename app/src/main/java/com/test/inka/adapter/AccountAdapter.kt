package com.test.inka.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.test.inka.R
import com.test.inka.databinding.ItemAccountBinding
import com.test.inka.model.DataResult

class AccountAdapter(
    private val accountList: ArrayList<DataResult>,
) : RecyclerView.Adapter<AccountAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {

            binding.tvChildname.text = dataList.name
            binding.tvBirth.text = dataList.birth
            binding.tvGender.text = dataList.gender
            binding.tvDesaKelurahan.text = dataList.desa_kelurahan_name
            binding.tvFather.text = dataList.father
            binding.tvMother.text = dataList.mother

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

            binding.parentDetails.setOnClickListener{
                Toast.makeText(it.context, "edit/hapus", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(accountList[position])
    }

    override fun getItemCount(): Int = accountList.size

}