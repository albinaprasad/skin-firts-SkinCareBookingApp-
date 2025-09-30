package com.medicalhealth.healthapplication.model.repository

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AppointmentRepository(val firestore: FirebaseFirestore) {

    fun fetchAllAppointments(): Flow<Resource<List<Appointment>>> = flow {
        emit(Resource.Loading())

        // TODO: Replace UID
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid

        val currentOne =
            firestore.collection("bookings").whereEqualTo("userId", currentUser).get().await()
//        val doctors = currentOne.documents.mapNotNull { documentSnapshot ->
//            documentSnapshot.toObject<Doctor>()?.copy(id = documentSnapshot.id)
//
//        }
        val snapshot = currentOne.documents.mapNotNull { it.toObject(Appointment::class.java) }
        Log.d("mathews", "fetchTheDoctor1: ${snapshot}")
        emit(Resource.Success(snapshot))



    }

    fun fetchTheDoctor(): Flow<Resource<List<Doctor>>> = flow {
        emit(Resource.Loading())
        val currentDoctor = firestore.collection("doctors").get().await()
        val doctorList = currentDoctor.documents.mapNotNull { it.toObject(Doctor::class.java) }
        emit(Resource.Success(doctorList))


    }

    fun ChangeTheStatus(documentId: String, status: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val currentPosition = firestore.collection("bookings").document(documentId)
            currentPosition.update("status", status).await()
            emit((Resource.Success(Unit)))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to update status: ${e.message}"))
            Log.d("mathews", "ChangeTheStatus: ${e.message}")
        }
    }
}





