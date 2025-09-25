package com.medicalhealth.healthapplication.model.data

import java.io.Serializable

data class  Doctor(
    val id: String = "",
    val name: String = "",
    val profileImageUrl: String = "",
    val specialization: String = "",
    val gender: Int = 0,
    val experience: Int = 0,
    val focus: String = "",
    val profile: String = "",
    val careerPath: String = "",
    val highlights: String = "",
    val startTime: Int = 0,
    val endTime: Int = 0,
    val startDay: Int = 0,
    val endDay: Int = 0,
    val rating: Double = 0.0,
    val commentCount: Int = 0,
   // val isFavourite:Boolean = false
): Serializable
