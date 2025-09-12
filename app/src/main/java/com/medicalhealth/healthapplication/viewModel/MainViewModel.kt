package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.Date
import com.medicalhealth.healthapplication.model.data.Doctor

class MainViewModel: ViewModel() {
    private val _dates = MutableLiveData<List<Date>?>()
    val dates: MutableLiveData<List<Date>?> get() = _dates

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointment: LiveData<List<Appointment>> get() = _appointments

    private val _doctors = MutableLiveData<List<Doctor>?>()
    val doctors: MutableLiveData<List<Doctor>?> get() = _doctors

    init {
        _dates.value = listOf(
            Date("9", "MON", false),
            Date("10", "TUE", false),
            Date("11", "WED", true),
            Date("12", "THU", false),
            Date("13", "FRI", true),
            Date("14", "SAT", true),
        )
        _appointments.value = listOf(
            Appointment("1", "10 AM", "Dr. Olivia Turner, M.D.", "Treatment and prevention of skin and photodermatitis."),
            Appointment("1", "10 AM", "Dr. Olivia Turner, M.D.", "Treatment and prevention of skin and photodermatitis."),
            Appointment("1", "10 AM", "Dr. Olivia Turner, M.D.", "Treatment and prevention of skin and photodermatitis.")
            ,Appointment("2", "10 AM", "Dr. Abc Turner, M.D.", "Treatment and prevention of skin and photodermatitis.")
            ,Appointment("2", "10 AM", "Dr. Abc Turner, M.D.", "Treatment and prevention of skin and photodermatitis.")
        )

        _doctors.value = listOf(
            Doctor("1", R.drawable.doctor_image_one, "Dr. Olivia Turner, M.D.", "Dermato-Endocrinology", 5.0, 60, true),
            Doctor("2", R.drawable.doctor_image_two, "Dr. Alexander Bennett, Ph.D.", "Dermato-Genetics", 4.5, 40, false),
            Doctor("3", R.drawable.doctor_image_three, "Dr. Sophia Martinez, Ph.D.", "Cosmetic Bioengineering", 5.0, 150, false),
            Doctor("4", R.drawable.doctor_image_four, "Dr. Michael Davidson, M.D.", "Nano-Dermatology", 4.8, 90, true),
        )
    }

    fun selectDate(selectedDate: Date){
        val updatedList = _dates.value?.map { date ->
            date.isSelected = (date == selectedDate)
            date
        }
        _dates.value = updatedList
    }

    fun toggleFavoriteStatus(doctorId: String){
        val updatedList = _doctors.value?.map { doctor ->
            if(doctor.id == doctorId){
                doctor.copy(isFavorite = !doctor.isFavorite)
            }else{
                doctor
            }
        }
        _doctors.value = updatedList
    }
}