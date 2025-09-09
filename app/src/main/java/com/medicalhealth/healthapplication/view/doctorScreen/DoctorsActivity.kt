package com.medicalhealth.healthapplication.view.doctorScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityDoctorsBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.view.adapter.DoctorListViewAdapter

class DoctorsActivity : BaseActivity() {
    lateinit var binding: ActivityDoctorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var doctorList= ArrayList<Doctor>()

        //TODO:Replace these dummy data with real data from firebase in the future

         doctorList = arrayListOf(
                Doctor(
                    id = "1",
                    profileImageUrl = R.drawable.doctor1,
                    name = "Dr. Sarah Johnson",
                    specialization = "Cardiologist",
                    rating = 4.8,
                    commentCount = 124,
                    isFavorite = false,

                ),
            Doctor(
                id = "2",
                profileImageUrl = R.drawable.doctor3,
                name = "Dr. Michael Chen",
                specialization = "Dermatologist",
                rating = 4.9,
                commentCount = 89,
                isFavorite = true,

            ),
            Doctor(
                id = "3",
                profileImageUrl = R.drawable.doctor_image_two,
                name = "Dr. Emily Rodriguez",
                specialization = "Pediatrician",
                rating = 4.7,
                commentCount = 156,
                isFavorite = false,

            ),
            Doctor(
                id = "4",
                profileImageUrl = R.drawable.doctor_img,
                name = "Dr. David Thompson",
                specialization = "Orthopedic Surgeon",
                rating = 4.6,
                commentCount = 73,
                isFavorite = true,

            ),
            Doctor(
                id = "5",
                profileImageUrl = R.drawable.doctor_image_two,
                name = "Dr. Lisa Patel",
                specialization = "Gynecologist",
                rating = 4.9,
                commentCount = 201,
                isFavorite = false,

            ),
            Doctor(
                id = "6",
                profileImageUrl = R.drawable.doctor_image_four,
                name = "Dr. James Wilson",
                specialization = "Neurologist",
                rating = 4.8,
                commentCount = 95,
                isFavorite = false,

            )
        )
        val adapter: DoctorListViewAdapter = DoctorListViewAdapter(this, doctorList)
        binding.doctorsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.doctorsRecyclerView.adapter = adapter
    }
}