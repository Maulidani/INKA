package com.test.inka.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.inka.ui.fragment.Vaccine2Fragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val total = 2

    override fun getItemCount(): Int = total

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> Vaccine2Fragment("jadwal")
            1 -> Vaccine2Fragment("manfaat")
            else -> Fragment()
        }

    }
}