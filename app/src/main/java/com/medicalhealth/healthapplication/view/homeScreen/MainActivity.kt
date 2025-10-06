package com.medicalhealth.healthapplication.view.homeScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityMainBinding
import com.medicalhealth.healthapplication.databinding.BottomNavigationLayoutBinding
import com.medicalhealth.healthapplication.utils.utils.getSystemBarInsets
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorsActivity
import com.medicalhealth.healthapplication.view.fragment.AllAppointmentFragment
import com.medicalhealth.healthapplication.view.profileScreen.ProfileFragment
import com.medicalhealth.healthapplication.view.reviewScreen.ReviewActivity
import dagger.hilt.android.AndroidEntryPoint
import com.medicalhealth.healthapplication.viewModel.SharedViewModel


@AndroidEntryPoint
class MainActivity : BaseActivity(){

    val viewModel: SharedViewModel by viewModels()
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var bottomNavBinding: BottomNavigationLayoutBinding

    companion object{
        const val FRAGMENT_TO_LOAD_KEY = "FRAGMENT_TO_LOAD"
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val fragmentToLoad = result.data?.getStringExtra(FRAGMENT_TO_LOAD_KEY)
            fragmentToLoad?.let { selectTab(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mainBinding.main) { v, insets ->
            insets.getSystemBarInsets(v) {
                mainBinding.root.setPadding(0, 0, 0, it.bottom)
            }
        }
        bottomNavBinding = BottomNavigationLayoutBinding.bind(mainBinding.bottomNavigationBar.root)
        val fragmentToLoad = intent.getStringExtra(FRAGMENT_TO_LOAD_KEY) ?: "home"
        selectTab(fragmentToLoad)
        setUpListeners()
    }

    private fun setUpListeners(){
        with(bottomNavBinding){
            homeButton.setOnClickListener {
                selectTab("home")
            }
            chatButton.setOnClickListener {
                selectTab("chat")
            }
            profileButton.setOnClickListener {
                selectTab("profile")
            }
            calenderButton.setOnClickListener {
                selectTab("calendar")
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main, fragment )
            .commit()
    }

    private fun selectTab(selectedIcon: String){
        resetAllIcon()
        when(selectedIcon){
            "home" -> {
                bottomNavBinding.homeButton.setImageResource(R.drawable.home_icon_blue)
                replaceFragment(HomeFragment())
            }
            "chat" -> bottomNavBinding.chatButton.setImageResource(R.drawable.chat_icon_blue)
            "profile" -> {
                bottomNavBinding.profileButton.setImageResource(R.drawable.profile_icon_blue)
                replaceFragment(ProfileFragment())
            }
            "calendar" -> {
                bottomNavBinding.calenderButton.setImageResource(R.drawable.calender_ic_blue)
                replaceFragment(AllAppointmentFragment())
            }

            else -> bottomNavBinding.chatButton.setImageResource(R.drawable.chat_icon_blue)
        }
    }

    private fun resetAllIcon(){
        with(bottomNavBinding){
            homeButton.setImageResource(R.drawable.home_icon_white)
            chatButton.setImageResource(R.drawable.chat_icon_white)
            profileButton.setImageResource(R.drawable.profile_icon_white)
            calenderButton.setImageResource(R.drawable.calender_icon_white)
        }
    }

     fun startDoctorActivity(){
        val intent = Intent(this@MainActivity, DoctorsActivity::class.java)
        resultLauncher.launch(intent)
    }

    fun startReviewActivity(){
        val intent = Intent(this@MainActivity, ReviewActivity::class.java)
        resultLauncher.launch(intent)
    }
}
