package com.medicalhealth.healthapplication.view.passwordManagerScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.view.BaseActivity
class PasswordManagerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_password_manager)

    }
}