package com.medicalhealth.healthapplication.model.repository.authenticationRepository

import com.google.firebase.auth.FirebaseUser
import com.medicalhealth.healthapplication.model.data.Users
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun signUp(email:String,password:String,userName:String,mobileNumber:Long,dob:String):Result<FirebaseUser?>
    suspend fun login(email:String,password:String):Result<FirebaseUser?>
    fun fetchCurrentUserDetails(): Flow<Resource<Users>>
}