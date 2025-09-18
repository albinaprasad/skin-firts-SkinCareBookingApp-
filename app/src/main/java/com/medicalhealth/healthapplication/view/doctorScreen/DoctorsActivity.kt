package com.medicalhealth.healthapplication.view.doctorScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityDoctorsBinding
import com.medicalhealth.healthapplication.utils.enums.Enums
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.DoctorsList
import com.medicalhealth.healthapplication.view.fragment.BottomNavigationFragment
import com.medicalhealth.healthapplication.view.homeScreen.MainActivity
import com.medicalhealth.healthapplication.viewModel.SharedViewModel

class DoctorsActivity : BaseActivity(), BottomNavigationFragment.OnFragmentSwitchListener {
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
    }

    private fun setUpOnObserver() {
        sharedViewModel.titleChange.observe(this){newTitle ->
            binding.titleText.text = newTitle
        }
    }

    override fun currentFragment(selectedFragment: Enums.MenuTypes) {
        val result = Intent()
        result.putExtra(MainActivity.SELECTED_OPTION, selectedFragment)
        setResult(RESULT_OK, result)
        finish()
    }
}