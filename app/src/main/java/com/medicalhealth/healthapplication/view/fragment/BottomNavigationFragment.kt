package com.medicalhealth.healthapplication.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.BottomNavigationLayoutBinding
import com.medicalhealth.healthapplication.utils.enums.Enums.*
import com.medicalhealth.healthapplication.view.homeScreen.HomeFragment
import com.medicalhealth.healthapplication.view.profileScreen.ProfileFragment

class BottomNavigationFragment: Fragment() {


    interface OnFragmentSwitchListener {
        fun currentFragment(selectedFragment: MenuTypes)
    }

    private lateinit var binding: BottomNavigationLayoutBinding
    private var fragmentSwitchListener: OnFragmentSwitchListener? = null
    private var selectedMenu = MenuTypes.HOME
    private var activeFragment: Fragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentSwitchListener) {
            fragmentSwitchListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListener()

        binding.homeButton.tag = MenuTypes.HOME
        binding.chatButton.tag = MenuTypes.MESSAGES
        binding.profileButton.tag = MenuTypes.PROFILE
        binding.calenderButton.tag = MenuTypes.CALENDER

        updateButtonState(selectedMenu)
    }

    fun setSelectedMenu(selectedMenu: MenuTypes) {
        this.selectedMenu = selectedMenu
        updateButtonState(selectedMenu)
    }


    private fun setUpListener() {
        with(binding) {
            homeButton.setOnClickListener {
                onMenuSelected(MenuTypes.HOME)
            }

            chatButton.setOnClickListener {
                onMenuSelected(MenuTypes.MESSAGES)
            }

            profileButton.setOnClickListener {
                onMenuSelected(MenuTypes.PROFILE)
            }

            calenderButton.setOnClickListener {
                onMenuSelected(MenuTypes.CALENDER)
            }
        }
    }

    private fun onMenuSelected(menuTypes: MenuTypes) {
        updateButtonState(menuTypes)
        when (menuTypes) {
            MenuTypes.HOME -> {
                showFragment(HomeFragment(), "Home", menuTypes)

            }

            MenuTypes.MESSAGES -> {

            }

            MenuTypes.PROFILE -> {
                showFragment(ProfileFragment(), "Profile", menuTypes)
            }

            MenuTypes.CALENDER -> {

            }
        }
    }

    private fun updateButtonState(selectedOption: MenuTypes) {
        listOf(
            binding.homeButton,
            binding.chatButton,
            binding.profileButton,
            binding.calenderButton,
        ).forEach {
            val menuType = it.tag as MenuTypes
            if (menuType == selectedOption) {
                it.setImageResource(menuType.getSelectedIcon())
            } else {
                it.setImageResource(menuType.getUnselectedIcon())
            }
        }
    }


    private fun showFragment(fragment: Fragment, tag: String, selectedMenu: MenuTypes) {
        val transaction = parentFragmentManager.beginTransaction()
        activeFragment?.let {
            transaction.hide(it)
        }
        var currentFragment = parentFragmentManager.findFragmentByTag(tag)

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
        fragmentSwitchListener?.currentFragment(selectedMenu)
    }

}
