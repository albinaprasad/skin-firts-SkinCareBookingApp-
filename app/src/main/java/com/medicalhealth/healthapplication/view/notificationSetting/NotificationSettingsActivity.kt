package com.medicalhealth.healthapplication.view.notificationSetting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge

import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.view.BaseActivity

class NotificationSettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notification_settings)

    }
}