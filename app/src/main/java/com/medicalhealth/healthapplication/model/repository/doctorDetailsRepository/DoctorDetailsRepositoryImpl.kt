package com.medicalhealth.healthapplication.model.repository.doctorDetailsRepository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.data.Users
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DoctorDetailsRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore):DoctorDetailsRepository {
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

    override suspend fun updateFavoriteDoctors(
        uid: String,
        favouriteList: List<String>
    ): Resource<Unit> {
         return try{
             firestore.collection("users").document(uid).update("favouriteDoctors", favouriteList).await()
             Resource.Success(Unit)
         }catch (e: Exception){
             Resource.Error("Firestore updated failed: ${e.localizedMessage}")
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

    override fun getFavoriteDoctors(uid: String): Flow<Resource<List<Doctor>>> = flow {

        emit(Resource.Loading())
        try {
            val userDoc =  firestore.collection("users").document(uid).get().await()
            val userObj = userDoc.toObject<Users>()
            val favoriteDoctorIdList = userObj?.favouriteDoctors ?: emptyList()

            if (favoriteDoctorIdList.isEmpty()) {
                emit(Resource.Success(emptyList()))
                return@flow
            }

            val doctors = mutableListOf<Doctor>()
            for(doctorID in favoriteDoctorIdList){
                val doctorDoc =  firestore.collection("doctors").document(doctorID).get().await()
                doctorDoc.toObject<Doctor>()?.copy( id = doctorDoc.id)?.let {
                    doctors.add(it)
                }
            }
            emit(Resource.Success(doctors))
        }
        catch (e: Exception){
            emit(Resource.Error("Error fetching favorite doctors: ${e.localizedMessage}"))
        }
    }
}