package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseUser
import com.medicalhealth.healthapplication.model.repository.authenticationRepository.AuthenticationRepositoryImpl
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthenticationViewModel(private val authenticationRepositoryImpl: AuthenticationRepositoryImpl = AuthenticationRepositoryImpl()): ViewModel(){

    private val _isResult = MutableLiveData<Result<FirebaseUser?>>()
    val isResult:LiveData<Result<FirebaseUser?>> = _isResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _isLogin = MutableLiveData<Result<FirebaseUser?>>()
    val isLogin:LiveData<Result<FirebaseUser?>> = _isLogin

    private val  _changePasswordState = MutableStateFlow<Resource<Unit>?>(null)
    val changePasswordState: StateFlow<Resource<Unit>?> = _changePasswordState.asStateFlow()


    fun changePassword(currentPassword: String, newPassword: String, confirmPassword: String){

        viewModelScope.launch {
            when {
                currentPassword.isEmpty()->{_changePasswordState.value = Resource.Error("Please enter current password")
                return@launch
                }
                newPassword.isEmpty() -> {
                    _changePasswordState.value = Resource.Error("Please enter new password")
                        return@launch
                    }
                confirmPassword.isEmpty()->{
                    _changePasswordState.value= Resource.Error("Please enter the new password")
                    return@launch
                }
                confirmPassword != newPassword -> {
                    _changePasswordState.value = Resource.Error("New password do not match")
                    return@launch
                }

            }
            _changePasswordState.value = Resource.Loading()
            val result = authenticationRepositoryImpl.changePassword(currentPassword, newPassword)
            _changePasswordState.value = result
        }
    }


    fun signUp(email:String,password:String,userName:String,mobileNumber:Long,dob:String){
        _isLoading.value = true

        viewModelScope.launch{
            val result =authenticationRepositoryImpl.signUp(email,password,userName,mobileNumber,dob)
            _isResult.value = result
            _isLoading.value = false
        }
    }
    fun logIn(email:String,password:String) {
        _isLoading.value = true

        viewModelScope.launch {
            val loginResult = authenticationRepositoryImpl.login(email,password)
            _isLogin.value = loginResult
            _isLoading.value = false
        }
    }
}