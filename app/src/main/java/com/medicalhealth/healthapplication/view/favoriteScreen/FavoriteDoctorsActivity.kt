package com.medicalhealth.healthapplication.view.favoriteScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityFavoriteDoctorsBinding
import com.medicalhealth.healthapplication.databinding.ActivityRatingBinding

class FavoriteDoctorsActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteDoctorsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFavoriteDoctorsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

    }
}