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
import com.test.inka.adapter.NotificationAdapter
import com.test.inka.databinding.FragmentNotificationBinding
import com.test.inka.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : Fragment() {
    private lateinit var sharedPref: PreferencesHelper

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: NotificationAdapter

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = PreferencesHelper(requireActivity())
        val id = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        notification(id)
    }

    private fun notification(id: String?) {
        ApiClient.instances.getNotification(id!!).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val result = response.body()?.result
                val message = response.body()?.message

                if (response.isSuccessful && value == "1") {

                    adapter = NotificationAdapter(result!!)
                    binding.rv.adapter = adapter
                    adapter.notifyDataSetChanged()
                    binding.tvKeterangan.visibility = View.INVISIBLE

                    if (result.isEmpty()){
                        binding.tvKeterangan.visibility = View.VISIBLE
                        binding.tvKeterangan.text = "Belum ada notifikasi"
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
}