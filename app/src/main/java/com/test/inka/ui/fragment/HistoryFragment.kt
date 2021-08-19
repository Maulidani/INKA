package com.test.inka.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.test.inka.adapter.RequestVaccineAdapter
import com.test.inka.databinding.FragmentHistoryBinding
import com.test.inka.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment(), RequestVaccineAdapter.iUserRecycler {
    private lateinit var sharedPref: PreferencesHelper

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RequestVaccineAdapter

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = PreferencesHelper(requireActivity())

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        val userId = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)
        val userType = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)
        if (userType == "admin") {
            getVaccineRequest("", "selesai")
        } else {
            getVaccineRequest(userId!!, "selesai")
        }

    }

    private fun getVaccineRequest(user: String, status: String) {
        ApiClient.instances.vaccineRequest(user, status).enqueue(object :
            Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message
                val result = response.body()?.result

                if (response.isSuccessful && value == "1") {
                    adapter = RequestVaccineAdapter(result!!, this@HistoryFragment)
                    binding.rv.adapter = adapter
                    adapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun refreshView() {}

}