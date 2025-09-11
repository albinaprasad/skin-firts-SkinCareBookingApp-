package com.medicalhealth.healthapplication.view.settingScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.medicalhealth.healthapplication.databinding.ActivitySettingsBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.notificationSetting.NotificationSettingsActivity
import com.medicalhealth.healthapplication.view.passwordManagerScreen.PasswordManagerActivity

class SettingsActivity : BaseActivity() {
    lateinit var settingBinding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        settingBinding= ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingBinding.root)

        buttonClickListener()
    }

    fun buttonClickListener()
    {
        with(settingBinding) {
            notificationSettingBtn.setOnClickListener {
                val settingsIntent =
                    Intent(this@SettingsActivity, NotificationSettingsActivity::class.java)
                startActivity(settingsIntent)
            }
            passwordManagerBtn.setOnClickListener {
                val passwordManagerIntent =
                    Intent(this@SettingsActivity, PasswordManagerActivity::class.java)
                startActivity(passwordManagerIntent)
            }
        }
    }

}