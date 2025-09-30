package com.medicalhealth.healthapplication.model.repository.doctorBooking

import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    suspend fun createBooking(booking: Appointment): Flow<Resource<Boolean>>
    suspend fun getBookedSlots(doctorId: String, date: String): Flow<Resource<List<String>>>
    suspend fun getUserBookings(userId: String): Flow<Resource<List<Appointment>>>
    fun getBookingsForCurrentUserAndMonth(startOfMonth: String, endOfMonth: String): Flow<Resource<List<Appointment>>>
}