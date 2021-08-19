package com.test.inka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.test.inka.databinding.ActivityMainBinding
import com.test.inka.model.DataResponse
import com.test.inka.ui.activity.LoginActivity
import com.test.inka.ui.activity.MenuActivity
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        sharedPref = PreferencesHelper(this)

        val id = sharedPref.getString(Constant.PREF_IS_LOGIN_ID)
        binding.tvName.text = sharedPref.getString(Constant.PREF_IS_LOGIN_NAME)
        binding.tvBirth.text = sharedPref.getString(Constant.PREF_IS_LOGIN_BIRTH)
        val userType = sharedPref.getString(Constant.PREF_IS_LOGIN_TYPE)

        binding.imgLogout.setOnClickListener {
            if (userType == "admin") {
                sharedPref.logout()
                Toast.makeText(this@MainActivity, "Keluar", Toast.LENGTH_SHORT).show()
                startActivity(Intent(Intent(this@MainActivity, LoginActivity::class.java)))
            } else {
                logout(id)
            }
        }

        if (userType == "admin") {
            binding.tvBirthInfo.text = userType
            binding.tvBirth.visibility = View.VISIBLE
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
        binding.cardThree.setOnClickListener {
            if (userType == "admin") {
                startActivity(
                    Intent(this, MenuActivity::class.java).putExtra(
                        "menu",
                        "account"
                    )
                )
            } else {
                startActivity(
                    Intent(this, MenuActivity::class.java).putExtra(
                        "menu",
                        "notification"
                    )
                )
            }
        }
    }

    private fun logout(id: String?) {
        ApiClient.instances.logout(id!!).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message

                if (response.isSuccessful && value == "1") {
                    sharedPref.logout()
                    Toast.makeText(this@MainActivity, "Keluar", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(Intent(this@MainActivity, LoginActivity::class.java)))
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        if (!sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            finish()
        }
    }
}