package com.medicalhealth.healthapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.medicalhealth.healthapplication.databinding.ActivitySetPasswordBinding
import com.medicalhealth.healthapplication.view.ui.loginScreen.LoginActivity

class SetPasswordActivity : BaseActivity() {
    private lateinit var binding:ActivitySetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSetPasswordbackbtn.setOnClickListener {
            val intent = Intent(this@SetPasswordActivity,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}