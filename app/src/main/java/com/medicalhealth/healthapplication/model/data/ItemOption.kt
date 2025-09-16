package com.medicalhealth.healthapplication.model.data

import androidx.annotation.DrawableRes

data class ItemOption(
    val optionId: String,
    @DrawableRes val iconResId: Int,
    val optionName: String,
    val showNextIcon: Boolean
)