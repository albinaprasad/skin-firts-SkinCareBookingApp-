package com.medicalhealth.healthapplication.model.repository.doctorDetailsRepository


import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DoctorDetailsRepository {
    suspend fun getDoctors(): Flow<Resource<List<Doctor>>>
    suspend fun updateFavoriteDoctors(uid: String, favouriteList: List<String>): Resource<Unit>
    fun addDoctor(doctor: Doctor)
}