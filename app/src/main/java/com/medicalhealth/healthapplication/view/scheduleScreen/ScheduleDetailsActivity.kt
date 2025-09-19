package com.medicalhealth.healthapplication.view.scheduleScreen

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityScheduleDetailsBinding
import com.medicalhealth.healthapplication.databinding.ItemDoctorProfileBinding
import com.medicalhealth.healthapplication.viewModel.SharedViewModel
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.view.BaseActivity

class ScheduleDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityScheduleDetailsBinding
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScheduleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDoctorData()
    }

    private fun setupDoctorData() {
        viewModel.selectedDoctor.observe(this) { selectedDoctor ->
            selectedDoctor?.let { doctor ->
                updateDoctorProfile(doctor)
            }
        }

        val doctor = Doctor(
            "4",
            "Dr. Michael Davidson, M.D.",
            "alexander_bennett",
            "Nano-Dermatology",
            15,
            "",
            "",
            "",
            "",
            9,
            17,
            0,
            7,
            4.8,
            90
        )

        viewModel.selectDoctor(doctor)
    }

    private fun updateDoctorProfile(doctor: Doctor) {
        val doctorProfileBinding = ItemDoctorProfileBinding.inflate(layoutInflater)
        val inputStream = assets.open("doctor_images/${doctor.profileImageUrl}.png")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        with(doctorProfileBinding) {
            doctorProfilePicture.setImageBitmap(bitmap)
            doctorNameTextView.text = doctor.name
            doctorSpecializationTextView.text = doctor.specialization
            ratingTextView.text = doctor.rating.toString()
            commentCountTextView.text = "${doctor.commentCount}"
        }
        binding.doctorCardContainer.removeAllViews()
        binding.doctorCardContainer.addView(doctorProfileBinding.root)

    }
}