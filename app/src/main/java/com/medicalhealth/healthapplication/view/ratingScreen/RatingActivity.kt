package com.medicalhealth.healthapplication.view.ratingScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.ActivityRatingBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.adapter.RatingsAdapter
import com.medicalhealth.healthapplication.view.favoriteScreen.FavoriteDoctorsActivity
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import kotlin.getValue
class RatingActivity : BaseActivity() {
    lateinit var binding: ActivityRatingBinding
    lateinit var adapter: RatingsAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        adapter = RatingsAdapter()

        setContentView(binding.root)
        with(binding) {
            ratingsRecyclerView.layoutManager = LinearLayoutManager(this@RatingActivity)
            ratingsRecyclerView.adapter = adapter
        }
        viewModel.doctors.observe(this@RatingActivity){doctors ->
            if (doctors != null) {
                adapter.updateData(doctors)
            }
        }

        buttonCliskListeners()
    }

    fun buttonCliskListeners()
    {
        with(binding)
        {
            binding.favBtn.setOnClickListener {
                val intent = Intent(this@RatingActivity, FavoriteDoctorsActivity::class.java)
                startActivity(intent)
            }

        }

    }
}