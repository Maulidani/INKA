package com.test.inka.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apotekku.projectapotekku.utils.PreferencesHelper
import com.google.android.material.tabs.TabLayoutMediator
import com.test.inka.R
import com.test.inka.adapter.ViewPagerAdapter
import com.test.inka.databinding.FragmentVaccineBinding

class VaccineFragment : Fragment() {
    private var _binding: FragmentVaccineBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVaccineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = ViewPagerAdapter(
            requireActivity().supportFragmentManager, lifecycle
        )
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Jadwal Imunisasi"
                1 -> tab.text = "Manfaat Imunisasi"
            }
        }.attach()
    }
}