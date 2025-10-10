package com.medicalhealth.healthapplication.model.repository.authenticationRepository

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.medicalhealth.healthapplication.model.data.Users
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    suspend fun signUp(email:String,password:String,userName:String,mobileNumber:Long,dob:String):Result<FirebaseUser?>
    suspend fun login(email:String,password:String):Result<FirebaseUser?>
    fun fetchCurrentUserDetails(): Flow<Resource<Users>>

   suspend fun changePassword(currentPassword:String,newPassword:String): Resource<Unit>

    suspend fun updateUserDetails(user:Users): Flow<Resource<Users>>
  fun  uploadProfileImage(imageUri: Uri, context: Context): Flow<Resource<String>>
}