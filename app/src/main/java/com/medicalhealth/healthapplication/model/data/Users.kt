package com.medicalhealth.healthapplication.model.data

data class Users (
    val uid:String = "",
    val userName:String = "",
    val userEmail:String = "",
    val mobileNumber:Long = 0,
    val dateOfBirth:String = "",
    val favouriteDoctors: List<String> = emptyList()
)