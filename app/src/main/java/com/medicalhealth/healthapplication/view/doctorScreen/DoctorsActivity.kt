package com.medicalhealth.healthapplication.view.doctorScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityDoctorsBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.DoctorsList
import com.medicalhealth.healthapplication.view.ratingScreen.RatingFragment
import com.medicalhealth.healthapplication.viewModel.SharedViewModel

class DoctorsActivity : BaseActivity() {
    lateinit var binding: ActivityDoctorsBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DoctorsList())
                .commit()
        }
        setUpOnObserver()
        buttonClickListener()
    }

    private fun buttonClickListener() {
        with(binding)
        {
           ratingBtn.setOnClickListener {
              ratingBtnClickSetUp()
            }
            sortButton.setOnClickListener {
                sharedViewModel.updateButtons("DoctorListFragment",binding.ratingBtn,binding.sortButton)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DoctorsList())
                    .commit()
            }
        }
    }

    private fun ratingBtnClickSetUp() {

        sharedViewModel.updateButtons("ratingFragment",binding.ratingBtn,binding.sortButton)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RatingFragment())
            .commit()
    }

    private fun setUpOnObserver() {
        sharedViewModel.titleChange.observe(this){newTitle ->
            binding.titleText.text = newTitle
        }
    }
}


