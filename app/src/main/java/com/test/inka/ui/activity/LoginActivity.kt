package com.test.inka.ui.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import apotekku.projectapotekku.utils.Constant
import apotekku.projectapotekku.utils.PreferencesHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.test.inka.MainActivity
import com.test.inka.databinding.ActivityLoginBinding
import com.test.inka.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        sharedPref = PreferencesHelper(this)

        binding.btnMasuk.setOnClickListener {
            val usernameGet = binding.etUsername.text.toString()
            val passwordGet = binding.etPassword.text.toString()

            if (usernameGet.isEmpty() || passwordGet.isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "tidak boleh ada yang kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                login(usernameGet, passwordGet)
            }
        }

        binding.tvDaftarDisini.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnPerawat.setOnClickListener {
            startActivity(Intent(this, LoginPerawatActivity::class.java))
        }

        createNotificationChannel()
        getToken()
    }

    private fun login(username: String, password: String) {
        ApiClient.instances.login(username, password).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message

                if (response.isSuccessful && value == "1") {
//                    Log.e("onResponse: ", response.body()?.id.toString())
//                    Log.e("onResponse: ", response.body()?.desa_kelurahan.toString())
//                    Log.e("onResponse: ", response.body()?.name.toString())
//                    Log.e("onResponse: ", response.body()?.birth.toString())
//                    Log.e("onResponse: ", response.body()?.gender.toString())
//                    Log.e("onResponse: ", response.body()?.mother.toString())
//                    Log.e("onResponse: ", response.body()?.father.toString())
//                    Log.e("onResponse: ", response.body()?.username.toString())
//                    Log.e("onResponse: ", response.body()?.password.toString())
//                    Log.e("onResponse: ", response.body()?.desa_kelurahan_name.toString())
//                    Log.e("onResponse: ", response.body()?.vaccine_date.toString())

                    registerToken(
                        token,
                        response.body()?.id,
                        response.body()?.name,
                        response.body()?.birth
                    )

                } else {
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun saveSession(id: String?, name: String?, birth: String?) {
        sharedPref.put(Constant.PREF_IS_LOGIN_ID, id.toString())
        sharedPref.put(Constant.PREF_IS_LOGIN_NAME, name.toString())
        sharedPref.put(Constant.PREF_IS_LOGIN_BIRTH, birth.toString())
        sharedPref.put(Constant.PREF_IS_LOGIN_TYPE, "pasien")
        sharedPref.put(Constant.PREF_IS_LOGIN, true)
        Toast.makeText(this@LoginActivity, "sukses", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun registerToken(token: String, idUser: String?, name: String?, birth: String?) {
        ApiClient.instances.registerToken(idUser!!, token).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message

                if (token != null) {

                    if (response.isSuccessful && value == "1") {

                        saveSession(idUser, name, birth)
                    } else {
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    getToken()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        if (sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    //get the app token
    private val CHANNEL_ID = "101"
    private lateinit var token: String

    private fun getToken() {
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
            val myToken = it.result!!.token
            Log.e("getToken1: ", myToken)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            token = task.result.toString()
        })
    }

    //create a notification channel
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Channel_Name"
            val descriptionText = "Channel_Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}