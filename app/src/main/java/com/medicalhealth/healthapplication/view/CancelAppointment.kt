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
            optionReschedulingLogic.setOnClickListener {
                rgReasons.check(R.id.option_Rescheduling_logic)
            }

            optionWeatherLogic.setOnClickListener {
                rgReasons.check(R.id.option_weather_logic)
            }

            optionUnexpectedLogic.setOnClickListener {
                rgReasons.check(R.id.option_Unexpected_logic)
            }

            optionOthersLogic.setOnClickListener {
                rgReasons.check(R.id.option_Others_logic)
            }
        }
        rgReasons.setOnCheckedChangeListener { _, checkedId ->
            updateVisuals(checkedId)
        }
    }

    private fun updateVisuals(checkedId: Int) {
        with(binding){

            optionReschedulingLogic.isChecked = false
            optionWeatherLogic.isChecked = false
            optionUnexpectedLogic.isChecked = false
            optionOthersLogic.isChecked = false


            binding.selectorRescheduling.isSelected = false
            binding.selectorWeather.isSelected = false
            binding.selectorUnexpected.isSelected = false
            binding.selectorOthers.isSelected = false

        }

        when (checkedId) {
            R.id.option_Rescheduling_logic -> {
                binding.optionReschedulingLogic.isChecked = true
                binding.selectorRescheduling.isSelected = true
            }
            R.id.option_weather_logic -> {
                binding.optionWeatherLogic.isChecked = true
                binding.selectorWeather.isSelected = true
            }
            R.id.option_Unexpected_logic -> {
                binding.optionUnexpectedLogic.isChecked = true
                binding.selectorUnexpected.isSelected = true
            }
            R.id.option_Others_logic -> {
                binding.optionOthersLogic.isChecked = true
                binding.selectorOthers.isSelected = true
            }
        }
    }
}