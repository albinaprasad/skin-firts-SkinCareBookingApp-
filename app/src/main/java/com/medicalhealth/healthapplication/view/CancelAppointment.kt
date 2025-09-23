package com.medicalhealth.healthapplication.view

import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityCancelAppointmentBinding

class CancelAppointment : BaseActivity() {

    private lateinit var binding: ActivityCancelAppointmentBinding
    private lateinit var rgReasons: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancelAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rgReasons = binding.rgCancelReasons
        with(binding){
            cvSelectorbar.setOnClickListener {
                rgReasons.check(R.id.option_Rescheduling_logic)
            }

            cvSelectorbarWeather.setOnClickListener {
                rgReasons.check(R.id.option_weather_logic)
            }

            cvSelectorbarUnexpected.setOnClickListener {
                rgReasons.check(R.id.option_Unexpected_logic)
            }

            cvSelectorbarOthers.setOnClickListener {
                rgReasons.check(R.id.option_Others_logic)
            }
        }
        rgReasons.setOnCheckedChangeListener { _, checkedId ->
            updateVisuals(checkedId)
        }
    }

    private fun updateVisuals(checkedId: Int) {
        with(binding){

            rbRescheduling.isChecked = false
            rbWeather.isChecked = false
            rbUnexpected.isChecked = false
            rbOthers.isChecked = false

            cvSelectorbar.isSelected = false
            cvSelectorbarWeather.isSelected = false
            cvSelectorbarUnexpected.isSelected = false
            cvSelectorbarOthers.isSelected = false
        }

        when (checkedId) {
            R.id.option_Rescheduling_logic -> {
                binding.rbRescheduling.isChecked = true
                binding.cvSelectorbar.isSelected = true
            }
            R.id.option_weather_logic -> {
                binding.rbWeather.isChecked = true
                binding.cvSelectorbarWeather.isSelected = true
            }
            R.id.option_Unexpected_logic -> {
                binding.rbUnexpected.isChecked = true
                binding.cvSelectorbarUnexpected.isSelected = true
            }
            R.id.option_Others_logic -> {
                binding.rbOthers.isChecked = true
                binding.cvSelectorbarOthers.isSelected = true
            }
        }
    }
}