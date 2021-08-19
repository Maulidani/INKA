package com.test.inka.ui.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.test.inka.R
import com.test.inka.databinding.ActivityRegisterBinding
import com.test.inka.model.DataResponse
import com.toyota.toyserv.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    var desaKelurahan = listOf("1", "1", "3", "4", "5", "6", "7")

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val adapter = ArrayAdapter(this, R.layout.list_dropdown, desaKelurahan)
        binding.inputDesaKelurahan.setAdapter(adapter)

        setDateTimeField()
        binding.inputBirth.setOnClickListener {
            datePickerDialog.show()
        }

        binding.btnregister.setOnClickListener {

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val awal = SimpleDateFormat("dd MMMM yyyy")

            val date: Date? = awal.parse(binding.inputBirth.text.toString())
            val birth: String = simpleDateFormat.format(date!!)

            val intSelectButton: Int = binding.rgGender.checkedRadioButtonId
            val rbGender: RadioButton? = findViewById(intSelectButton)

            val childName = binding.etChildName.text.toString()
            val gender = rbGender?.text.toString()
            val desaKelurahan = binding.inputDesaKelurahan.text.toString()
            val father = binding.etFather.text.toString()
            val mother = binding.etMother.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (childName.isEmpty() ||
                birth.isEmpty() ||
                gender.isEmpty() ||
                desaKelurahan.isEmpty() ||
                father.isEmpty() ||
                mother.isEmpty() ||
                username.isEmpty() ||
                password.isEmpty()
            ) {
                Toast.makeText(this, "tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()
            } else {

                register(
                    childName,
                    birth,
                    gender,
                    desaKelurahan,
                    father,
                    mother,
                    username,
                    password
                )
            }
        }
    }

    private fun register(
        childName: String,
        birth: String,
        gender: String,
        desaKelurahan: String,
        father: String,
        mother: String,
        username: String,
        password: String
    ) {
        ApiClient.instances.register(
            childName,
            birth,
            gender,
            desaKelurahan,
            father,
            mother,
            username,
            password
        ).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                val value = response.body()?.value
                val message = response.body()?.message

                if (response.isSuccessful && value == "1") {
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private lateinit var datePickerDialog: DatePickerDialog

    @SuppressLint("SimpleDateFormat")
    private fun setDateTimeField() {
        val newCalendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                val sd = SimpleDateFormat("dd MMMM yyyy")
                val startDate = newDate.time
                val fDate = sd.format(startDate)
                binding.inputBirth.setText(fDate)
            }, newCalendar[Calendar.YEAR], newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
    }

}