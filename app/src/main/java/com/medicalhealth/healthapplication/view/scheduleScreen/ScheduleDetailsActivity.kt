package com.medicalhealth.healthapplication.view.scheduleScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.medicalhealth.healthapplication.databinding.ActivityScheduleDetailsBinding
import com.medicalhealth.healthapplication.view.BaseActivity

class ScheduleDetailsActivity : BaseActivity() {
    lateinit var binding: ActivityScheduleDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityScheduleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}