package com.medicalhealth.healthapplication.model.repository.doctorBooking

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.medicalhealth.healthapplication.model.data.DoctorBooking
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class BookingRepositoryImpl(val firestore:FirebaseFirestore): BookingRepository{
    override suspend fun createBooking(booking: DoctorBooking): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            firestore.collection("bookings")
                .add(booking)
                .await()

            emit(Resource.Success(true))
        } catch (e: Exception) {

            emit(Resource.Error("Failed to create booking"))
        }
    }
    override suspend fun getBookedSlots(
        doctorId: String,
        date: String
    ): Flow<Resource<List<String>>> = flow{
        emit(Resource.Loading())
        try {
            val snapshot=firestore.collection("bookings")
                .whereEqualTo("doctorId", doctorId)
                .whereEqualTo("bookingDate", date)
                .get()
                .await()

            val bookedItems = snapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.getString("bookingTime")
            }
            emit(Resource.Success(bookedItems))
        }
        catch (e:Exception)
        {
            Log.e("BookingRepo", "Error fetching booked slots")
            emit(Resource.Error("Failed to check availability"))
        }
    }

    override suspend fun getUserBookings(userId: String): Flow<Resource<List<DoctorBooking>>> = flow {

        emit(Resource.Loading())
        try {
            val snapshot=firestore.collection("bookings")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val userBookings = snapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject<DoctorBooking>()
            }
            emit(Resource.Success(userBookings))
        }
        catch (e:Exception)
        {
            Log.e("BookingRepo", "Error fetching user booking")
            emit(Resource.Error("Failed to fetch user booking"))
        }
    }


}