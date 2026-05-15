package com.gramaUrja

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            // Clear ALL old saved data every single time
            getSharedPreferences("grama_urja", MODE_PRIVATE)
                .edit().clear().apply()

            // Always go to Welcome → Login
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()

        }, 2000)
    }
}
