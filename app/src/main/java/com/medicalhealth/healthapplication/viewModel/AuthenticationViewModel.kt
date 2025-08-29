package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseUser
import com.medicalhealth.healthapplication.model.repository.AuthenticationRepository

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepository= AuthenticationRepository()): ViewModel(){

    private val _isResult = MutableLiveData<Result<FirebaseUser?>>()
    val isResult:LiveData<Result<FirebaseUser?>> = _isResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading


    fun signUp(email:String,password:String,userName:String,mobileNumber:Long,dob:String){
        _isLoading.value = true

        viewModelScope.launch{
            val result =authenticationRepository.signUp(email,password,userName,mobileNumber,dob)
            _isResult.value = result
            _isLoading.value = false
        }
    }

}