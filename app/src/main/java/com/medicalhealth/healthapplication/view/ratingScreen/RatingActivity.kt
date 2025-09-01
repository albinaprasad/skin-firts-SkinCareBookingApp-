package com.medicalhealth.healthapplication.view.ratingScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import com.medicalhealth.healthapplication.databinding.ActivityRatingBinding
import com.medicalhealth.healthapplication.view.adapter.RatingsAdapter
import com.medicalhealth.healthapplication.view.favoriteScreen.FavoriteDoctorsActivity
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import kotlin.getValue

class RatingActivity : AppCompatActivity() {
    lateinit var binding: ActivityRatingBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        with(binding) {
            viewModel.doctors.observe(this@RatingActivity) { doctorList ->
                val adapter = RatingsAdapter(doctorList)
                ratingsRecyclerView.layoutManager = LinearLayoutManager(this@RatingActivity)
                ratingsRecyclerView.adapter = adapter
            }
        }

        binding.favBtn.setOnClickListener {
            val intent = Intent(this, FavoriteDoctorsActivity::class.java)
            startActivity(intent)
        }
    }
}