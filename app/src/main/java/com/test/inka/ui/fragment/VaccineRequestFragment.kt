package com.test.inka.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.inka.adapter.RequestVaccineAdapter
import com.test.inka.databinding.FragmentVaccineRequestBinding
import com.test.inka.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VaccineRequestFragment : Fragment(), RequestVaccineAdapter.iUserRecycler {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RequestVaccineAdapter

    private var _binding: FragmentVaccineRequestBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVaccineRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        getVaccineRequest("", "belum_selesai")

        binding.fabAdd.setOnClickListener {
            binding.parentAdd.visibility = View.VISIBLE
        }
        binding.btnCancel.setOnClickListener {
            binding.parentAdd.visibility = View.INVISIBLE
        }
        binding.btnAdd.setOnClickListener {
            addVaccineRequest("","1","5","","belum_selesai")
        }
    }

    private fun getVaccineRequest(user: String, status: String) {
        ApiClient.instances.vaccineRequest(user, status)
            .enqueue(object : Callback<DataResponse> {
                override fun onResponse(
                    call: Call<DataResponse>,
                    response: Response<DataResponse>
                ) {
                    val value = response.body()?.value
                    val message = response.body()?.message
                    val result = response.body()?.result

                    if (response.isSuccessful && value == "1") {
                        adapter = RequestVaccineAdapter(result!!, this@VaccineRequestFragment)
                        binding.rv.adapter = adapter
                        adapter.notifyDataSetChanged()

                    } else {
                        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    override fun refreshView() {
        getVaccineRequest("", "belum_selesai")
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
                        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}