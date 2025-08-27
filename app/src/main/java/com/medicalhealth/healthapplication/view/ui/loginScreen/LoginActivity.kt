package com.medicalhealth.healthapplication.view.ui.loginScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityLoginBinding
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorsActivity

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.login.setOnClickListener {
            val intent = Intent(this, DoctorsActivity::class.java)
            startActivity(intent)
        }
    }
}