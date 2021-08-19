package com.test.inka.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.test.inka.R
import com.test.inka.adapter.AccountAdapter
import com.test.inka.adapter.RequestVaccineAdapter
import com.test.inka.databinding.FragmentVaccineRequestBinding
import com.test.inka.model.DataResponse
import com.test.inka.model.DataResult
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VaccineRequestFragment : Fragment(), RequestVaccineAdapter.iUserRecycler {
    private lateinit var sharedPref: PreferencesHelper

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RequestVaccineAdapter

    private var _binding: FragmentVaccineRequestBinding? = null
    private val binding get() = _binding!!

    private var listOfName = ArrayList<String>()

    private var vaccineId = listOf(
        "vit k & HB-0",
        "BCG & Polio 1",
        "DPT-1 & Polio 2",
        "DPT-2 & Polio 3",
        "DPT-3 & Polio 4",
        "Campak",
        "DPT Tambahan",
        "Campak Tambahan",
    )

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
        sharedPref = PreferencesHelper(requireActivity())

        val adapterVaccine =
            ArrayAdapter(requireActivity(), R.layout.list_dropdown, vaccineId)
        binding.inputVaccine.setAdapter(adapterVaccine)

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        val userId = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)
        val userType = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)
        if (userType == "admin") {
            getVaccineRequest("", "belum_selesai")
        } else {
            binding.fabAdd.visibility = View.INVISIBLE
            getVaccineRequest(userId!!, "belum_selesai")
        }

        binding.fabAdd.setOnClickListener {
            binding.parentAdd.visibility = View.VISIBLE
            getAccount()
            binding.btnAdd.visibility = View.INVISIBLE
        }
        binding.btnCancel.setOnClickListener {
            binding.parentAdd.visibility = View.INVISIBLE
        }
        binding.btnAdd.setOnClickListener {

            val childName = binding.inputChildName.text.toString()
            val vaccine = binding.inputVaccine.text.toString()

            if (childName.isEmpty() || vaccine.isEmpty()){
                Toast.makeText(requireActivity(), "data tidak boleh ada y6ang kosong", Toast.LENGTH_SHORT).show()
            } else {
                addVaccineRequest("", childName, vaccine, "", "belum_selesai")
            }
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
                        binding.parentAdd.visibility = View.INVISIBLE
                        binding.tvKeterangan.visibility = View.INVISIBLE

                        if (result.isEmpty()){
                            binding.tvKeterangan.visibility = View.VISIBLE
                            binding.tvKeterangan.text = "Belum ada jadwal imunisasi"
                        }
                    } else {
                        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                        binding.tvKeterangan.visibility = View.VISIBLE
                        binding.tvKeterangan.text = "Terjadi Kesalahan"
                    }
                }

                override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    binding.tvKeterangan.visibility = View.VISIBLE
                    binding.tvKeterangan.text = "Periksa koneksi internet anda"
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
                        getVaccineRequest("", "belum_selesai")

                        binding.inputChildName.text = null
                        binding.inputVaccine.text = null
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

    private fun getAccount() {
        ApiClient.instances.account().enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message
                val result = response.body()?.result

                if (response.isSuccessful && value == "1") {

                    for (i in result!!) {
                        var no = 1
                        listOfName.add(i.name)
                        no += 1
                    }

                    Log.e("result", listOfName.toString())
                    val adapterName =
                        ArrayAdapter(requireActivity(), R.layout.list_dropdown, listOfName)
                    binding.inputChildName.setAdapter(adapterName)

                    binding.btnAdd.visibility = View.VISIBLE

                } else {
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

}