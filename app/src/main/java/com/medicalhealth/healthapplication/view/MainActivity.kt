package com.medicalhealth.healthapplication.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import com.medicalhealth.healthapplication.databinding.ActivityMainBinding
import com.medicalhealth.healthapplication.view.fragment.BottomNavigationFragment


class MainActivity : BaseActivity(), BottomNavigationFragment.OnFragmentSwitchListener {
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

    }

    override fun currentFragment(selectedFragment: BottomNavigationFragment.MenuTypes) {
        Log.d("message", "Current Fragment: $selectedFragment")
    }


}
