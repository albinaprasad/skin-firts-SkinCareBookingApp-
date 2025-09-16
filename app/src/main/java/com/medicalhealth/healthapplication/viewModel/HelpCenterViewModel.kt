package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.ItemOption

class HelpCenterViewModel:ViewModel() {

    private val _itemOptions = MutableLiveData<List<ItemOption>>()
    val itemOptions: LiveData<List<ItemOption>> = _itemOptions

    init {
        _itemOptions.value = listOf(
            ItemOption("1", R.drawable.microphone_blue, "Customer Service", true),
            ItemOption("2", R.drawable.website_blue, "Website", true),
            ItemOption("3", R.drawable.whatsapp_blue, "Whatsapp", true),
            ItemOption("4", R.drawable.facebook_blue, "Facebook", true),
            ItemOption("5", R.drawable.instagram_blue, "Instagram", true)
        )
    }
}