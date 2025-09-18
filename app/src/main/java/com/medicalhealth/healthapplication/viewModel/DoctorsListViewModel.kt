package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.Date
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.data.Schedule
import com.medicalhealth.healthapplication.view.adapter.DoctorListViewAdapter


class DoctorsListViewModel: ViewModel() {

    private val _doctors = MutableLiveData<List<Doctor>>()
    val doctors: LiveData<List<Doctor>> get() = _doctors

    private val _date = MutableLiveData<List<Schedule>>()
    val date: LiveData<List<Schedule>> get() = _date

    init {

        _doctors.value = listOf(
            Doctor("1", R.drawable.doctor_image_one, "Dr. Olivia Turner, M.D.", "Dermato-Endocrinology", 5.0, 60, true),
            Doctor("2", R.drawable.doctor_image_two, "Dr. Alexander Bennett, Ph.D.", "Dermato-Genetics", 4.5, 40, false),
            Doctor("3", R.drawable.doctor_image_three, "Dr. Sophia Martinez, Ph.D.", "Cosmetic Bioengineering", 5.0, 150, false),
            Doctor("4", R.drawable.doctor_image_four, "Dr. Michael Davidson, M.D.", "Nano-Dermatology", 4.8, 90, true),
        )

        _date.value = listOf(
            Schedule("Sunday,12 June","9.30AM-10.00AM"),
            Schedule("Friday,20 June","2.30PM-3.00PM"),
            Schedule("Tuesday,15 June","9.30AM-10.00AM"),
            Schedule("Monday,14 June","3.00PM-3.30PM")
        )


    }
}
