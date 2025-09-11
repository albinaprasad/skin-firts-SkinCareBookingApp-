package com.medicalhealth.healthapplication.view

import androidx.appcompat.app.AppCompatActivity
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.view.fragment.BottomNavigationFragment

abstract class BaseActivity : AppCompatActivity() {


    fun setSelectedMenu(menuTypes: BottomNavigationFragment.MenuTypes) {
        val menuFragment = supportFragmentManager.findFragmentById(R.id.fragmentNavigation)
        if (menuFragment is BottomNavigationFragment) {
            menuFragment.setSelectedMenu(menuTypes)
        }
    }
}