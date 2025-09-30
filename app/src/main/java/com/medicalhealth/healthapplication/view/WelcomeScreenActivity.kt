package com.medicalhealth.healthapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.medicalhealth.healthapplication.databinding.ActivityWelcomeScreenBinding
import com.medicalhealth.healthapplication.view.homeScreen.MainActivity
import com.medicalhealth.healthapplication.view.ui.loginScreen.LoginActivity
import com.medicalhealth.healthapplication.viewModel.MainViewModel

class WelcomeScreenActivity : BaseActivity() {
    private lateinit var binding: ActivityWelcomeScreenBinding
    private val viewModel: MainViewModel by viewModels()
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

    override fun onStart() {
        super.onStart()
        val currentUser = viewModel.keepMeSignedIn()
        if(currentUser != null){
            val intent = Intent(this@WelcomeScreenActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }


}