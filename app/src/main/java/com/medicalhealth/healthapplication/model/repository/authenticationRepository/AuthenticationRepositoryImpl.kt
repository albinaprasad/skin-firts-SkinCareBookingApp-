package com.medicalhealth.healthapplication.model.repository.authenticationRepository

import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.medicalhealth.healthapplication.model.data.Users
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class AuthenticationRepositoryImpl: AuthenticationRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("users")

    override suspend fun signUp(email:String, password:String, userName:String, mobileNumber:Long, dob:String):Result<FirebaseUser?> {
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
    override suspend fun login(email:String, password:String):Result<FirebaseUser?>{
        return try{
            val userAuth = auth.signInWithEmailAndPassword(email,password).await()
            Result.success(userAuth.user)
        }
        catch (e:Exception){
            Result.failure(e)
        }
    }

    override fun fetchCurrentUserDetails(): Flow<Resource<Users>> = flow {
        emit(Resource.Loading())
        try{
            val currentId = getCurrentUser()?.uid
            Log.d("message", "Repo Current User Id: $currentId")
            if(currentId == null){
                emit(Resource.Error("Error: User is not authenticated."))
                return@flow
            }

            val documentSnapshot = firestore.collection("users").document(currentId).get().await()
            Log.d("message", "document: $documentSnapshot")

            if (documentSnapshot.exists()) {
                val currentUser = documentSnapshot.toObject<Users>()
                Log.d("message", "UserDetails: $currentUser")
                if(currentUser != null) {
                    emit(Resource.Success(currentUser))
                }
            } else {
                Log.d("message", "UserDetails: Document does not exist for ID $currentId")
            }

        }catch (e: Exception){
            emit(Resource.Error("Error while current user info: ${e.localizedMessage}"))
        }
    }

    override suspend  fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Resource<Unit> {
        return  try  {
            val user= auth.currentUser
            if(user == null){
                return Resource.Error("User not authenticated")
            }
            val email= user.email
            if(email == null){
                return Resource.Error("email not authenticated")
            }
        val credential = EmailAuthProvider.getCredential(email, currentPassword)

        try {
            user.reauthenticate(credential).await()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            return Resource.Error("Current password is incorrect")
        } catch (e: Exception) {
            return Resource.Error("Authentication failed: ${e.localizedMessage}")
        }

        try {
            user.updatePassword(newPassword).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to update password: ${e.localizedMessage}")
        }

    } catch (e: Exception) {
        Resource.Error("Error changing password: ${e.localizedMessage}")
    }
}



    fun signOut(){
        auth.signOut()
    }

    fun getCurrentUser():FirebaseUser?{
        return auth.currentUser
    }
}