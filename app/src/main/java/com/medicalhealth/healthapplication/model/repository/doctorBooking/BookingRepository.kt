package com.medicalhealth.healthapplication.model.repository.doctorBooking

import com.medicalhealth.healthapplication.model.data.DoctorBooking
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    suspend fun createBooking(booking: DoctorBooking): Flow<Resource<Boolean>>
    suspend fun getBookedSlots(doctorId: String, date: String): Flow<Resource<List<String>>>
    suspend fun getUserBookings(userId: String): Flow<Resource<List<DoctorBooking>>>

}