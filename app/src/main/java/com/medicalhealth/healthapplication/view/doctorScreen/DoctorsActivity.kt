package com.medicalhealth.healthapplication.view.doctorScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityDoctorsBinding
import com.medicalhealth.healthapplication.databinding.BottomNavigationLayoutBinding
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.utils.utils.getSystemBarInsets
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.favoriteScreen.FavouriteDoctorsFragment
import com.medicalhealth.healthapplication.view.homeScreen.MainActivity
import com.medicalhealth.healthapplication.view.ratingScreen.RatingFragment
import com.medicalhealth.healthapplication.view.settingScreen.SettingsActivity
import com.medicalhealth.healthapplication.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DoctorsActivity : BaseActivity() {
    lateinit var binding: ActivityDoctorsBinding
    val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var bottomNavBinding: BottomNavigationLayoutBinding
    var showDoctorInfo by Delegates.notNull<Boolean>()

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

         showDoctorInfo = intent.getBooleanExtra("SHOW_DOCTOR_INFO", false)
        getDoctorInfo()

        if (savedInstanceState == null) {
            if (showDoctorInfo) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_doctor, DoctorInfoFragment())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_doctor, DoctorsListFragment())
                    .commit()
            }
        }

        bottomNavBinding = BottomNavigationLayoutBinding.bind(binding.bottomNavigationBar.root)
        setUpListeners()
        setUpOnObserver()
        buttonClickListener()
    }
    private fun getDoctorInfo() {
        if (showDoctorInfo) {
            val doctor = intent.getSerializableExtra("doctor_object") as? Doctor
            doctor?.let { sharedViewModel.selectDoctor(it) }
        }
    }

    private fun buttonClickListener() {
        with(binding)
        {
            ratingBtn.setOnClickListener {
                filterBtnClickSetUp("RatingFragment", getString(R.string.rating), RatingFragment(), binding.ratingBtn, binding.sortButton, binding.favBtn, binding.femaleBtn, binding.maleBtn)
            }

            sortButton.setOnClickListener {
                filterBtnClickSetUp("DoctorListFragment", getString(R.string.doctors), DoctorsListFragment.newInstance("ALL"), binding.ratingBtn, binding.sortButton, binding.favBtn, binding.femaleBtn, binding.maleBtn)
            }

            favBtn.setOnClickListener {
                filterBtnClickSetUp("FavouriteFragment", getString(R.string.favorite), FavouriteDoctorsFragment(), binding.ratingBtn, binding.sortButton, binding.favBtn, binding.femaleBtn, binding.maleBtn)
            }

            femaleBtn.setOnClickListener {
                filterBtnClickSetUp("FemaleListFragment", getString(R.string.female), DoctorsListFragment.newInstance("Female"), binding.ratingBtn, binding.sortButton, binding.favBtn, binding.femaleBtn, binding.maleBtn)
            }

            maleBtn.setOnClickListener {
                filterBtnClickSetUp("MaleListFragment", getString(R.string.male), DoctorsListFragment.newInstance("Male"), binding.ratingBtn, binding.sortButton, binding.favBtn, binding.femaleBtn, binding.maleBtn)
            }

            backButton.setOnClickListener {
               onBackPressed()
            }
            settingsButton.setOnClickListener {
                val intent = Intent(this@DoctorsActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
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

    private fun filterBtnClickSetUp(
        fragmentName: String,
        titleName: String,
        fragment: Fragment,
        ratingBtn: ImageButton,
        sortButton: Button,
        favBtn: ImageButton,
        femaleBtn: ImageButton,
        maleBtn: ImageButton
    ) {
        sharedViewModel.updateButtons(
            fragmentName,
            ratingBtn,
            sortButton,
            favBtn,
            femaleBtn,
            maleBtn
        )
        sharedViewModel.setTitle(titleName)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_doctor, fragment)
            .commit()
    }
}


