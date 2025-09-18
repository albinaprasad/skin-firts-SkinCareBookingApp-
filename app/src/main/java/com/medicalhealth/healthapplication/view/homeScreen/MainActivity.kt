package com.medicalhealth.healthapplication.view.homeScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityMainBinding
import com.medicalhealth.healthapplication.utils.enums.Enums
import com.medicalhealth.healthapplication.utils.enums.Enums.MenuTypes
import com.medicalhealth.healthapplication.utils.enums.Enums.MenuTypes.*
import com.medicalhealth.healthapplication.utils.utils.getSystemBarInsets
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorsActivity
import com.medicalhealth.healthapplication.view.fragment.BottomNavigationFragment
import com.medicalhealth.healthapplication.view.profileScreen.ProfileFragment


class MainActivity : BaseActivity(), BottomNavigationFragment.OnFragmentSwitchListener {

    companion object {
        const val SELECTED_OPTION = "SELECTED_OPTION"
    }

    private lateinit var mainBinding: ActivityMainBinding
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ) {
        if (it.resultCode == RESULT_OK) {
            val selectedMenu = it.data?.getSerializableExtra(SELECTED_OPTION) as MenuTypes
            bottomNavigationFragment?.onMenuSelected(selectedMenu)
        }
    }

    private var activeFragment: Fragment? = null
    private var bottomNavigationFragment: BottomNavigationFragment? = null


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

        bottomNavigationFragment = supportFragmentManager.findFragmentById(R.id.fragmentNavigation) as BottomNavigationFragment?
    }

    override fun currentFragment(selectedFragment: MenuTypes) {
        Log.d("message", "Current Fragment: $selectedFragment")
        when (selectedFragment) {
            HOME ->{
                  showFragment(HomeFragment(), "Home", selectedFragment)
            }
            MESSAGES ->{

            }
            PROFILE ->{
                 showFragment(ProfileFragment(), "Profile", selectedFragment)
            }
            CALENDER ->{

            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String, selectedMenu: MenuTypes) {
        val transaction = supportFragmentManager.beginTransaction()
        activeFragment?.let {
            transaction.hide(it)
        }
        var currentFragment = supportFragmentManager.findFragmentByTag(tag)

        if (currentFragment == null) {
            currentFragment = fragment
            transaction.add(R.id.fragment_container, currentFragment, tag)
            Log.d("message", "if if if -> ${currentFragment.tag}")
        } else {
            transaction.show(currentFragment)
            Log.d("message", "else else -> ${currentFragment.tag}")
        }

        transaction.commit()
        activeFragment = currentFragment
    }

    fun startDoctorActivity() {
        val intent = Intent(this, DoctorsActivity::class.java)
        resultLauncher.launch(intent)
    }
}
