package com.medicalhealth.healthapplication.view.profileScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.medicalhealth.healthapplication.databinding.ActivityEditProfileBinding
import com.medicalhealth.healthapplication.view.BaseActivity

class EditProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}