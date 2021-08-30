package com.test.inka.ui.fragment

import android.graphics.ColorSpace
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.test.inka.adapter.ManfaatAdapter
import com.test.inka.adapter.RequestVaccineAdapter
import com.test.inka.databinding.FragmentVaccine2Binding
import com.test.inka.model.DataResponse
import com.test.inka.model.Manfaat
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Vaccine2Fragment(_item: String) : Fragment(), RequestVaccineAdapter.iUserRecycler {
    private var _binding: FragmentVaccine2Binding? = null
    private val binding get() = _binding!!
    private val item = _item

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVaccine2Binding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var sharedPref: PreferencesHelper

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RequestVaccineAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = PreferencesHelper(requireActivity())

        val userId = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)
        val userType = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)

        layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.layoutManager = layoutManager

        when (item) {
            "jadwal" -> jadwal(userId!!, "belum_selesai")
            "manfaat" -> manfaat()
            else -> Toast.makeText(requireActivity(), "error viewpager", Toast.LENGTH_SHORT).show()
        }

    }

    private fun jadwal(user: String, status: String) {
        binding.parentJadwal.visibility = View.VISIBLE
        binding.parentManfaat.visibility = View.GONE

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
                        adapter = RequestVaccineAdapter(result!!, this@Vaccine2Fragment)
                        binding.rv.adapter = adapter
                        adapter.notifyDataSetChanged()
                        binding.tvKeterangan.visibility = View.INVISIBLE

                        if (result.isEmpty()) {
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

    lateinit var listData: ArrayList<Manfaat>
    lateinit var adapterManfaat: ManfaatAdapter

    private fun manfaat() {
        binding.parentJadwal.visibility = View.GONE
        binding.parentManfaat.visibility = View.VISIBLE

        listData = ArrayList<Manfaat>()
        listData.add(
            Manfaat(
                "Jenis Imunisasi : Hepatitis B",
                "Usia Pemberian : 0 - 7 hari",
                "Jumlah Pemberian : 1",
                "Untuk mencegah infeksi hati akibat virus hepatits B."
            )
        )
        listData.add(
            Manfaat(
                "Jenis Imunisasi : BCG",
                "Usia Pemberian : 1 bulan",
                "Jumlah Pemberian : 1",
                "Untuk Pemberian kekebalan aktif terhadap tuberkulosis."
            )
        )
        listData.add(
            Manfaat(
                "Jenis Imunisasi : Polo / IPV",
                "Usia Pemberian : 1, 2, 3, 4 bulan",
                "Jumlah Pemberian : 4",
                "Untuk pencegahan poliomyelitis pada bayi dan anak immunocompromi sed, kontak di lingkungan keluarga dan pada individu di mana vaksin polio oral menjadi kontra indikasi."
            )
        )
        listData.add(
            Manfaat(
                "Jenis Imunisasi : DPT - HB - Hib",
                "Usia Pemberian : 2, 3, 4, 18 bulan",
                "Jumlah Pemberian : 4",
                "Untuk pencegahan terhadap difteri, tetanus, pertussis (batuk rejan), hepatitis B dan infeksi Haemophilus influenzae tipe b secara simultan."
            )
        )
        listData.add(
            Manfaat(
                "Jenis Imunisasi : Campak",
                "Usia Pemberian : 9, 24 bulan",
                "Jumlah Pemberian : 4",
                "UUntuk pemberian kekebalan aktif terhadap penyakit campak."
            )
        )

        adapterManfaat = ManfaatAdapter(listData)

        binding.rvManfaat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvManfaat.adapter = adapterManfaat

    }

    override fun refreshView() {
        //
    }
}