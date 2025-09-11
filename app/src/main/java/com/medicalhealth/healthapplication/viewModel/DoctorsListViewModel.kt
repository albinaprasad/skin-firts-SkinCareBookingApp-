package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.Date
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorListViewAdapter
import com.medicalhealth.healthapplication.view.doctorScreen.Doctors

class DoctorsListViewModel: ViewModel() {
   

    private val _doctors = MutableLiveData<List<Doctors>>()
    val doctors: LiveData<List<Doctors>> get() = _doctors








    init {


        _doctors.value = listOf(
             Doctors("Dr. Olivia Turner, M.D.", "Cardiologist", R.drawable.doctor1),
            Doctors("Dr. Michael Davidson,\n M.D.", "Neurologist", R.drawable.doctor_img),
            Doctors("Dr. Brown", "Pediatrician", R.drawable.doctor3),
             Doctors("Dr. Sachin", "ortho", R.drawable.doctor1),
             Doctors("Dr. Sachin", "ortho", R.drawable.doctor1)
        )
    }
}
