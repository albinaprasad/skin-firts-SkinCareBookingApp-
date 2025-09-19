package com.medicalhealth.healthapplication.model.repository


import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DoctorDetailsRepository {
    suspend fun getDoctors(): Flow<Resource<List<Doctor>>>
    fun addDoctor(doctor: Doctor)
}