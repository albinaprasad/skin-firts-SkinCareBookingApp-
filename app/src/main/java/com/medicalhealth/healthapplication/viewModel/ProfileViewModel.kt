package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.ItemOption

class ProfileViewModel: ViewModel() {

    private val _itemOptions = MutableLiveData<List<ItemOption>>()
    val itemOptions: LiveData<List<ItemOption>> = _itemOptions

    init {
        _itemOptions.value = listOf(
            ItemOption("1", R.drawable.profile_ic_img, "Profile", true),
            ItemOption("2", R.drawable.favorite_ic_img, "Favorite", true),
            ItemOption("3", R.drawable.payment_ic_img, "Payment Method", true),
            ItemOption("4", R.drawable.privacy_ic_img, "Privacy Policy", true),
            ItemOption("5", R.drawable.settings_ic_img, "Settings", true),
            ItemOption("6", R.drawable.help_ic_img, "Help", true),
            ItemOption("7", R.drawable.logout_ic_img, "Logout", false)
        )
    }
}