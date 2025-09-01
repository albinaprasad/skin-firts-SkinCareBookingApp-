package com.medicalhealth.healthapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.medicalhealth.healthapplication.databinding.ActivityWelcomeScreenBinding
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorsActivity
import com.medicalhealth.healthapplication.model.data.Notification
import com.medicalhealth.healthapplication.view.notificationScreen.NotificationActivity
import com.medicalhealth.healthapplication.view.ui.loginScreen.LoginActivity

class WelcomeScreenActivity : BaseActivity() {
    private lateinit var binding: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnlogin.setOnClickListener {
            val Loginintent = Intent(this, LoginActivity::class.java)
            startActivity(Loginintent)
        }
        binding.btnsignup.setOnClickListener {
            val intent = Intent(this@WelcomeScreenActivity,SignupActivity::class.java)
            startActivity(intent)
        }

    }
}