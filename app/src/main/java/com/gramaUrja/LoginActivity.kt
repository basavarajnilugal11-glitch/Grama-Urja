package com.gramaUrja

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gramaUrja.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide skip — login is mandatory so name is always saved
        binding.tvSkip.visibility = View.GONE

        binding.btnLogin.setOnClickListener {
            val name  = binding.etName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val zone  = binding.etZone.text.toString().trim()

            when {
                name.isEmpty()    -> binding.etName.error  = "Enter your name"
                phone.isEmpty()   -> binding.etPhone.error = "Enter mobile number"
                phone.length < 10 -> binding.etPhone.error = "Enter valid 10-digit number"
                zone.isEmpty()    -> binding.etZone.error  = "Enter your village/zone"
                else -> {
                    getSharedPreferences("grama_urja", MODE_PRIVATE)
                        .edit()
                        .putString("user_name", name)
                        .putString("user_phone", phone)
                        .putString("user_zone", zone)
                        .putBoolean("is_logged_in", true)
                        .apply()

                    Toast.makeText(this, "Welcome, $name! 🌱", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        binding.chipHebbalaguppe.setOnClickListener  { binding.etZone.setText("Hebbalaguppe")  }
        binding.chipMalavalli.setOnClickListener     { binding.etZone.setText("Malavalli")     }
        binding.chipSrirangapatna.setOnClickListener { binding.etZone.setText("Srirangapatna") }
    }
}