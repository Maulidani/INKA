package com.test.inka.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.inka.R
import com.test.inka.databinding.ItemVaccineRequestBinding
import com.test.inka.model.DataResponse
import com.test.inka.model.DataResult
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestVaccineAdapter(
    private val vaccineList: ArrayList<DataResult>,
    private val mListener: iUserRecycler
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

            if (dataList.status == "selesai") {
                binding.btnSelesai.visibility = View.GONE
                binding.tvDateInfo.text = "Telah melakukan imunisasi : "
                binding.tvDate.text = dataList.vaccined_date
            } else {
                binding.btnSelesai.visibility = View.VISIBLE
            }

            binding.btnSelesai.setOnClickListener {
                upDateVaccineRequest(dataList.id, "", "", "selesai", "selesai")
            }
        }

        private fun upDateVaccineRequest(
            id: String,
            user: String,
            vaccine: String,
            vaccinedDate: String,
            status: String
        ) {
            ApiClient.instances.vaccineRequestPost(id, user, vaccine, vaccinedDate, status)
                .enqueue(object :
                    Callback<DataResponse> {
                    override fun onResponse(
                        call: Call<DataResponse>,
                        response: Response<DataResponse>
                    ) {
                        val value = response.body()?.value
                        val message = response.body()?.message

                        if (response.isSuccessful && value == "1") {
                            Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                            addVaccineRequest("", binding.tvName.text.toString(), binding.tvImmunization.text.toString(),"jadwalkan_next","belum_selesai")
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

        private fun addVaccineRequest(
            id: String,
            user: String,
            vaccine: String,
            vaccinedDate: String,
            status: String
        ) {
            ApiClient.instances.vaccineRequestPost(id, user, vaccine, vaccinedDate, status)
                .enqueue(object :
                    Callback<DataResponse> {
                    override fun onResponse(
                        call: Call<DataResponse>,
                        response: Response<DataResponse>
                    ) {
                        val value = response.body()?.value
                        val message = response.body()?.message

                        if (response.isSuccessful && value == "1") {
                            Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
                        }
                        mListener.refreshView()
                    }

                    override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                        Toast.makeText(itemView.context, t.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                })
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

    interface iUserRecycler {
        fun refreshView()
    }
}