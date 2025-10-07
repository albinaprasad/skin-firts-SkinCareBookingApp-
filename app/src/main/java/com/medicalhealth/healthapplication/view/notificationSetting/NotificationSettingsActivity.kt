package com.medicalhealth.healthapplication.view.notificationSetting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityNotificationSettingsBinding
import com.medicalhealth.healthapplication.view.BaseActivity

class NotificationSettingsActivity : BaseActivity() {
    lateinit var binding: ActivityNotificationSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonClickListeners()
    }

    fun buttonClickListeners(){
        with(binding){
            backButton.setOnClickListener {
                onBackPressed()
            }
        }
    }
}