package com.test.inka.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.test.inka.R
import com.test.inka.databinding.ItemAccountBinding
import com.test.inka.model.DataResponse
import com.test.inka.model.DataResult
import com.test.inka.ui.activity.RegisterActivity
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountAdapter(
    private val accountList: ArrayList<DataResult>, private val mListener: iUserRecycler
) : RecyclerView.Adapter<AccountAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dataList: DataResult) {

            binding.tvChildname.text = dataList.name
            binding.tvName.text = dataList.name
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

            binding.parentDetails.setOnClickListener {
                optionAlert(itemView, dataList)
            }
        }

        private fun optionAlert(
            itemView: View,
            dataResult: DataResult
        ) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Aksi")

            val options = arrayOf("Edit akun", "Hapus akun")
            builder.setItems(
                options
            ) { _, which ->
                when (which) {
                    0 -> {
                        itemView.context.startActivity(
                            Intent(itemView.context, RegisterActivity::class.java)
                                .putExtra("id", dataResult.id)
                                .putExtra("name", dataResult.name)
                                .putExtra("birth", dataResult.birth)
                                .putExtra("gender", dataResult.gender)
                                .putExtra("desa_kelurahan_name", dataResult.desa_kelurahan_name)
                                .putExtra("father", dataResult.father)
                                .putExtra("mother", dataResult.mother)
                                .putExtra("username", dataResult.username)
                                .putExtra("password", dataResult.password)
                                .putExtra("intent", true)
                        )
                    }

                    1 -> deleteAlert(itemView, dataResult)
                }
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        private fun deleteAlert(itemView: View, dataResult: DataResult) {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Hapus")
            builder.setMessage("Hapus akun ?")

            builder.setPositiveButton("Ya") { _, _ ->
                delete(itemView, dataResult)
            }

            builder.setNegativeButton("Tidak") { _, _ ->
                // cancel
            }
            builder.show()
        }

        private fun delete(itemView: View, dataResult: DataResult) {

            ApiClient.instances.deleteAccount(dataResult.id).enqueue(object :
                Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val message = response.body()?.message

                    if (response.isSuccessful && value == "1") {
                        Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                        mListener.refreshView()
                    } else {
                        Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(itemView.context, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
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

    interface iUserRecycler {
        fun refreshView()
    }
}