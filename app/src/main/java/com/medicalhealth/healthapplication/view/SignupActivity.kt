package com.medicalhealth.healthapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivitySignupBinding
import com.medicalhealth.healthapplication.view.ui.loginScreen.LoginActivity

class SignupActivity : BaseActivity() {
    private lateinit var binding:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackSignUp.setOnClickListener {
            val intent = Intent(this@SignupActivity,WelcomeScreenActivity::class.java)
            startActivity(intent)
        }
        binding.tvlogin
            .setOnClickListener {
            val intent = Intent(this@SignupActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        }
    }
