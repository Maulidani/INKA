package com.test.inka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.inka.databinding.ActivityMainBinding
import com.test.inka.ui.activity.MenuActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val userType = "admin"
        if (userType == "admin") {
            binding.tvCardthree.text = "akun"
        }

        binding.cardOne.setOnClickListener {
            startActivity(
                Intent(this, MenuActivity::class.java).putExtra(
                    "menu",
                    "request_vaccine"
                )
            )
        }
        binding.cardTwo.setOnClickListener {
            startActivity(
                Intent(this, MenuActivity::class.java).putExtra(
                    "menu",
                    "history"
                )
            )
        }
    }
}