package com.medicalhealth.healthapplication.model.data

data class Date(
    val dayOfMonth: String,
    val dayOfWeek: String,
    var isSelected: Boolean = false,
    var isToday: Boolean = false
)
