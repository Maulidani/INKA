package com.test.inka.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.test.inka.MainActivity
import com.test.inka.databinding.ActivityLoginPerawatBinding
import com.test.inka.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPerawatActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var binding: ActivityLoginPerawatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPerawatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        sharedPref = PreferencesHelper(this)

        binding.btnMasuk.setOnClickListener {
            val usernameGet = binding.etUsername.text.toString()
            val passwordGet = binding.etPassword.text.toString()

            if (usernameGet.isEmpty() || passwordGet.isEmpty()) {
                Toast.makeText(
                    this,
                    "tidak boleh ada yang kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                login(usernameGet, passwordGet)
            }
        }
    }

    private fun login(username: String, password: String) {
        ApiClient.instances.loginAdmin(username, password).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message

                if (response.isSuccessful && value == "1") {

                    sharedPref.put(Constant.PREF_IS_LOGIN_NAME, response.body()!!.name)
                    sharedPref.put(Constant.PREF_IS_LOGIN_TYPE, "admin")
                    sharedPref.put(Constant.PREF_IS_LOGIN, true)

                    Toast.makeText(this@LoginPerawatActivity, "sukses", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginPerawatActivity, MainActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(this@LoginPerawatActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(this@LoginPerawatActivity, t.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
}