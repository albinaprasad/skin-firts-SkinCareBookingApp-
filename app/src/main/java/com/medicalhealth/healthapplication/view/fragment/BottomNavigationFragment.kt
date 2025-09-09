package com.medicalhealth.healthapplication.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.BottomNavigationLayoutBinding
import com.medicalhealth.healthapplication.view.MainActivity
import com.medicalhealth.healthapplication.view.ProfileActivity
import com.medicalhealth.healthapplication.view.fragment.BottomNavigationFragment.MenuTypes.*

class BottomNavigationFragment: Fragment() {

    private lateinit var binding: BottomNavigationLayoutBinding
    private var selectedMenu = HOME

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

        binding.homeButton.tag = HOME
        binding.chatButton.tag = MESSAGES
        binding.profileButton.tag = PROFILE
        binding.calenderButton.tag = CALENDER

        updateButtonState(selectedMenu)
    }

    fun setSelectedMenu(selectedMenu: MenuTypes) {
        this.selectedMenu = selectedMenu
        updateButtonState(selectedMenu)
    }


    private fun setUpListener(){
        with(binding){
            homeButton.setOnClickListener {
                onMenuSelected(HOME)
            }

            chatButton.setOnClickListener {
                onMenuSelected(MESSAGES)
            }

            profileButton.setOnClickListener {
                onMenuSelected(PROFILE)
            }

            calenderButton.setOnClickListener {
                onMenuSelected(CALENDER)
            }
        }
    }

    private fun onMenuSelected(menuTypes: MenuTypes) {
        updateButtonState(menuTypes)
        when (menuTypes) {
            HOME -> {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            MESSAGES -> {

            }
            PROFILE -> {
                val intent = Intent(activity, ProfileActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            CALENDER -> {

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

    enum class MenuTypes {
        HOME, MESSAGES, PROFILE, CALENDER;

        fun getSelectedIcon(): Int {
            return when (this) {
                HOME -> R.drawable.home_icon_blue
                MESSAGES -> R.drawable.chat_icon_blue
                PROFILE -> R.drawable.profile_icon_blue
                CALENDER -> R.drawable.calender_ic_blue
            }
        }

        fun getUnselectedIcon(): Int {
                return when (this) {
                    HOME -> R.drawable.home_icon_white
                    MESSAGES -> R.drawable.chat_icon_white
                    PROFILE -> R.drawable.profile_icon_white
                    CALENDER -> R.drawable.calender_icon_white
                }

        }
    }

}