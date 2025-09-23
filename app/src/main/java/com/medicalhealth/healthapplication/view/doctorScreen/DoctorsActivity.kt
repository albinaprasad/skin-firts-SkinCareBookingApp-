package com.medicalhealth.healthapplication.view.doctorScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityDoctorsBinding
import com.medicalhealth.healthapplication.databinding.BottomNavigationLayoutBinding
import com.medicalhealth.healthapplication.utils.utils.getSystemBarInsets
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.favoriteScreen.FavouriteDoctorsFragment
import com.medicalhealth.healthapplication.view.homeScreen.MainActivity
import com.medicalhealth.healthapplication.view.ratingScreen.RatingFragment
import com.medicalhealth.healthapplication.viewModel.SharedViewModel

class DoctorsActivity : BaseActivity() {
    lateinit var binding: ActivityDoctorsBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var bottomNavBinding: BottomNavigationLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            insets.getSystemBarInsets(v) {
                binding.root.setPadding(0, 0, 0, it.bottom)
            }
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_doctor, DoctorsListFragment())
                .commit()
        }
        bottomNavBinding = BottomNavigationLayoutBinding.bind(binding.bottomNavigationBar.root)
        setUpListeners()
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
                sharedViewModel.updateButtons(
                    "DoctorListFragment",
                    binding.ratingBtn,
                    binding.sortButton,
                    binding.favBtn
                )
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_doctor, DoctorsListFragment())
                    .commit()
            }

            favBtn.setOnClickListener {
                sharedViewModel.updateButtons(
                    "FavouriteFragment",
                    binding.ratingBtn,
                    binding.sortButton,
                    binding.favBtn
                )
               sharedViewModel.setTitle(getString(R.string.favorite))
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_doctor, FavouriteDoctorsFragment())
                    .commit()
            }
        }
    }

    private fun ratingBtnClickSetUp() {

        sharedViewModel.updateButtons(
            "ratingFragment",
            binding.ratingBtn,
            binding.sortButton,
            binding.favBtn
        )
        sharedViewModel.setTitle(getString(R.string.rating))
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_doctor, RatingFragment())
            .commit()
    }

    private fun setUpListeners(){
        with(bottomNavBinding){
            homeButton.setOnClickListener {
                returnToMain("home")
            }
            chatButton.setOnClickListener {
                returnToMain("chat")
            }
            profileButton.setOnClickListener {
                returnToMain("profile")
            }
            calenderButton.setOnClickListener {
                returnToMain("calendar")
            }
        }
    }

    private fun setUpOnObserver() {
        sharedViewModel.titleChange.observe(this) { newTitle ->
            binding.titleText.text = newTitle
        }
    }

    private fun returnToMain(tab: String){
        val resultIntent = Intent()
        resultIntent.putExtra(MainActivity.FRAGMENT_TO_LOAD_KEY, tab)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

}


