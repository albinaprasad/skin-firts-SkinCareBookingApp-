package com.medicalhealth.healthapplication.view.homeScreen

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import com.medicalhealth.healthapplication.databinding.ActivityMainBinding
import com.medicalhealth.healthapplication.utils.utils.getSystemBarInsets
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.fragment.BottomNavigationFragment


class MainActivity : BaseActivity(), BottomNavigationFragment.OnFragmentSwitchListener {
    private lateinit var mainBinding: ActivityMainBinding

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
    }

    override fun currentFragment(selectedFragment: BottomNavigationFragment.MenuTypes) {
        Log.d("message", "Current Fragment: $selectedFragment")
    }
}
