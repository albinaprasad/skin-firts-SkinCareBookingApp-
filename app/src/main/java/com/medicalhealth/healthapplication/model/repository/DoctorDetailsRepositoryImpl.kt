package com.medicalhealth.healthapplication.model.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class DoctorDetailsRepositoryImpl(private val firestore: FirebaseFirestore):DoctorDetailsRepository {
    override suspend fun getDoctors(): Flow<Resource<List<Doctor>>> = flow {
        emit(Resource.Loading())
        try{
            val snapshot = firestore.collection("doctors").get().await()
            val doctors = snapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject<Doctor>()?.copy(id = documentSnapshot.id)
            }
            emit(Resource.Success(doctors))
        }catch (e: Exception){
            emit(Resource.Error("Error while fetching doctor info: ${e.localizedMessage}"))
        }
    }

    override fun addDoctor(doctor: Doctor) {
        firestore.collection("doctors").add(doctor).addOnSuccessListener { documentRef ->
            Log.d("message", "Document Added: $documentRef")
        }
            .addOnFailureListener { e->
                Log.w("message", "Error adding document")
            }
    }
}