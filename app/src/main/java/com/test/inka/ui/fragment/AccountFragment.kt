package com.test.inka.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.inka.adapter.AccountAdapter
import com.test.inka.databinding.FragmentAccountBinding
import com.test.inka.model.DataResponse
import com.test.inka.ui.activity.RegisterActivity
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: AccountAdapter

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        getAccount()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireActivity(), RegisterActivity::class.java))
        }
    }

    private fun getAccount() {
        ApiClient.instances.account().enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message
                val result = response.body()?.result

                if (response.isSuccessful && value == "1") {
                    adapter = AccountAdapter(result!!)
                    binding.rv.adapter = adapter
                    adapter.notifyDataSetChanged()
                    binding.tvKeterangan.visibility = View.INVISIBLE

                    if (result.isEmpty()){
                        binding.tvKeterangan.visibility = View.VISIBLE
                        binding.tvKeterangan.text = "Belum ada akun"
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

    override fun onResume() {
        super.onResume()
        getAccount()
    }
}