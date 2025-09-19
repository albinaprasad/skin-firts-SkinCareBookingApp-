package com.medicalhealth.healthapplication.view.favoriteScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.ActivityFavoriteDoctorsBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.adapter.FavDoctorAdapter
import com.medicalhealth.healthapplication.view.ratingScreen.RatingActivity
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import kotlin.getValue
class FavoriteDoctorsActivity : BaseActivity() {
    lateinit var docbinding: ActivityFavoriteDoctorsBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        docbinding = ActivityFavoriteDoctorsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        val adapter = FavDoctorAdapter()
        setContentView(docbinding.root)
        with(docbinding) {

            ratingsRecyclerView.layoutManager = LinearLayoutManager(this@FavoriteDoctorsActivity)
            ratingsRecyclerView.adapter = adapter
            viewModel.dummyDoctors.observe(this@FavoriteDoctorsActivity) { doctorList ->
                if (doctorList != null) {
                    adapter.updateData(doctorList)
                }
            }

            ratingButton.setOnClickListener {
                val intent = Intent(this@FavoriteDoctorsActivity, RatingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
