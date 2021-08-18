package com.test.inka.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.test.inka.R
import com.test.inka.databinding.ActivityMenuBinding
import com.test.inka.ui.fragment.HistoryFragment
import com.test.inka.ui.fragment.VaccineRequestFragment

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intentMenu = intent.getStringExtra("menu")

        if (intentMenu == "request_vaccine") {
            loadFragment(VaccineRequestFragment())
        } else if (intentMenu == "history") {
            loadFragment(HistoryFragment())

        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }
}