package com.medicalhealth.healthapplication.view.passwordManagerScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.medicalhealth.healthapplication.databinding.ActivityPasswordManagerBinding
import com.medicalhealth.healthapplication.view.BaseActivity
class PasswordManagerActivity : BaseActivity() {
    lateinit var binding: ActivityPasswordManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPasswordManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}