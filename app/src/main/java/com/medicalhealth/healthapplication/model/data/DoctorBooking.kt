package com.medicalhealth.healthapplication.model.data

import java.io.Serializable

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
    val status: String = "",
    val personType:String="YOURSELF",
    val createdAt: Long = System.currentTimeMillis()
): Serializable
