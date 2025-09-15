package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.Date
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.view.adapter.DoctorListViewAdapter

class SharedViewModel: ViewModel() {


    private val _selectedDoctor = MutableLiveData<Doctor>()
    val selectedDoctor: LiveData<Doctor> = _selectedDoctor

    private val _titleChange = MutableLiveData<String>()
    val titleChange:LiveData<String> = _titleChange

    fun selectDoctor(doctor: Doctor) {
        _selectedDoctor.value = doctor
    }
    fun setTitle(title:String){
        _titleChange.value = title
    }








}
