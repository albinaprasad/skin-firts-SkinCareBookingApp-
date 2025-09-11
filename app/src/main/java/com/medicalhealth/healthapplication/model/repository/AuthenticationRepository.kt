package com.medicalhealth.healthapplication.model.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.medicalhealth.healthapplication.model.data.Users
import kotlinx.coroutines.tasks.await


class AuthenticationRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("Users")

    suspend fun signUp(email:String,password:String,userName:String,mobileNumber:Long,dob:String):Result<FirebaseUser?> {
        return try {
            val userAuth = auth.createUserWithEmailAndPassword(email,password).await()
            val firebaseUser = userAuth.user

            if(firebaseUser!=null) {
                val profile = Users(
                    uid = firebaseUser.uid,
                    userName = userName,
                    userEmail = firebaseUser.email ?: "",
                    mobileNumber = mobileNumber,
                    dateOfBirth = dob
                )
                userCollection.document(firebaseUser.uid).set(profile)
                    .addOnSuccessListener {
                        Log.d("signup", "signup successful")
                    }
                    .addOnFailureListener { e ->
                        Log.d("signup","signup failed", e)
                    }
                    .await()
            }
                Result.success(userAuth.user)





        }
        catch (e:Exception){

            Result.failure(e)

        }
    }
    suspend fun login(email:String,password:String):Result<FirebaseUser?>{
        return try{
            val userAuth = auth.signInWithEmailAndPassword(email,password).await()
            Result.success(userAuth.user)

        }
        catch (e:Exception){
            Result.failure(e)
        }
    }
}