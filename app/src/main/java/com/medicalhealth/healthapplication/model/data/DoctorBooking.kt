package com.medicalhealth.healthapplication.model.data

data class DoctorBooking(
    val id: String = "",
    val userId: String = "",
    val doctorId: String = "",
    val patientFullName: String = "",
    val patientAge: Int = 0,
    val patientGender: String = "",
    val problemDescription: String = "",
    val bookingDate: String = "", // "2025-09-22" format
    val bookingTime: String = "", // "09:30 AM" format
    val status: String = "UPCOMING",
    val createdAt: Long = System.currentTimeMillis()
)
