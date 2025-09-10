package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.ProfileOption

class ProfileViewModel: ViewModel() {

    private val _profileOptions = MutableLiveData<List<ProfileOption>>()
    val profileOptions: LiveData<List<ProfileOption>> = _profileOptions

    init {
        _profileOptions.value = listOf(
            ProfileOption("1", R.drawable.profile_ic_img, "Profile", true),
            ProfileOption("2", R.drawable.favorite_ic_img, "Favorite", true),
            ProfileOption("3", R.drawable.payment_ic_img, "Payment Method", true),
            ProfileOption("4", R.drawable.privacy_ic_img, "Privacy Policy", true),
            ProfileOption("5", R.drawable.settings_ic_img, "Settings", true),
            ProfileOption("6", R.drawable.help_ic_img, "Help", true),
            ProfileOption("7", R.drawable.logout_ic_img, "Logout", false)
        )
    }
}