package com.medicalhealth.healthapplication.model.data

data class Doctor(
    val id: String,
    val profileImageUrl: Int,
    val name: String,
    val specialization: String,
    val rating: Double,
    val commentCount: Int,
    var isFavorite: Boolean = false
)
