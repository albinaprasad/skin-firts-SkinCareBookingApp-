package com.medicalhealth.healthapplication.view.favoriteScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.ActivityFavoriteDoctorsBinding
import com.medicalhealth.healthapplication.view.adapter.FavDoctorAdapter
import com.medicalhealth.healthapplication.view.adapter.RatingsAdapter
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import kotlin.getValue

class FavoriteDoctorsActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteDoctorsBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDoctorsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        with(binding) {
            viewModel.doctors.observe(this@FavoriteDoctorsActivity) { doctorList ->
                val adapter = FavDoctorAdapter(doctorList)
                ratingsRecyclerView.layoutManager =
                    LinearLayoutManager(this@FavoriteDoctorsActivity)
                ratingsRecyclerView.adapter = adapter
            }
        }
    }
}