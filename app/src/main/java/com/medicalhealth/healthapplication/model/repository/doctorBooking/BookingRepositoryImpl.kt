package com.medicalhealth.healthapplication.model.repository.doctorBooking

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class BookingRepositoryImpl(val firestore:FirebaseFirestore): BookingRepository{
    override suspend fun createBooking(booking: Appointment): Flow<Resource<Boolean>> = flow {
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

    override suspend fun getUserBookings(userId: String): Flow<Resource<List<Appointment>>> = flow {

        emit(Resource.Loading())
        try {
            val snapshot=firestore.collection("bookings")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val userBookings = snapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject<Appointment>()
            }
            emit(Resource.Success(userBookings))
        }
        catch (e:Exception)
        {
            Log.e("BookingRepo", "Error fetching user booking")
            emit(Resource.Error("Failed to fetch user booking"))
        }
    }

    override fun getBookingsForCurrentUserAndMonth(
        startOfMonth: String,
        endOfMonth: String
    ): Flow<Resource<List<Appointment>>> = flow {
        emit(Resource.Loading())

        // TODO: User ID Retrieval
        var currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            ?: run {
                emit(Resource.Error("User not authenticated."))
                return@flow
            }
        Log.d("message", currentUserId)
        try{
            Log.d("message", "getBookingsForCurrentUserAndMonth: try${startOfMonth}, ${endOfMonth}")
            val docs = firestore.collection("bookings")
                .whereEqualTo("userId", currentUserId).get().await()


            val userBookings = docs.documents.mapNotNull { doc ->
                val date = doc.getString("bookingDate")
                val status = doc.getString("status")
                if(date != null && date >= startOfMonth && date <= endOfMonth && status == "UPCOMING" ){
                    val userId = doc.getString("userId") ?: return@mapNotNull null
                    val doctorId = doc.getString("doctorId") ?: ""
                    val patientFullName = doc.getString("patientFullName") ?: ""
                    val patientAge = doc.getLong("patientAge")?.toInt() ?: 0
                    val patientGender = doc.getString("patientGender") ?: ""
                    val problemDescription = doc.getString("problemDescription") ?: ""
                    val bookingTime = doc.getString("bookingTime") ?: ""
                    val status = doc.getString("status") ?: ""
                    val personType = doc.getString("personType") ?: "YOURSELF"
                    val createdAt = doc.getLong("createdAt") ?: System.currentTimeMillis()

                    Appointment(
                        userId = userId,
                        doctorId = doctorId,
                        patientFullName = patientFullName,
                        patientAge = patientAge,
                        patientGender = patientGender,
                        problemDescription = problemDescription,
                        bookingDate = date,
                        bookingTime = bookingTime,
                        status = status,
                        personType = personType,
                        createdAt = createdAt
                    )
                } else {
                    null
                }
            }
            emit(Resource.Success(userBookings))
        }
        catch (e: Exception){
            emit(Resource.Error("Error fetching bookings: ${e.localizedMessage}"))
        }
    }


}