package com.medicalhealth.healthapplication.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.repository.DoctorDetailsRepository
import com.medicalhealth.healthapplication.model.repository.DoctorDetailsRepositoryImpl
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.medicalhealth.healthapplication.model.data.Schedule
import com.medicalhealth.healthapplication.view.adapter.DoctorListViewAdapter

class DoctorsListViewModel(private val repository: DoctorDetailsRepository = DoctorDetailsRepositoryImpl(
    FirebaseFirestore.getInstance())) : ViewModel() {

    private val _doctors = MutableStateFlow<Resource<List<Doctor>>>(
        value = Resource.Loading()
    )
    val doctors: StateFlow<Resource<List<Doctor>>> = _doctors

    private val _maleDoctors = MutableStateFlow<Resource<List<Doctor>>>(Resource.Loading())
    val maleDoctors: StateFlow<Resource<List<Doctor>>> = _maleDoctors

    private val _femaleDoctors = MutableStateFlow<Resource<List<Doctor>>>(Resource.Loading())
    val femaleDoctors: StateFlow<Resource<List<Doctor>>> = _femaleDoctors

    private val _date = MutableLiveData<List<Schedule>>()
    val date: LiveData<List<Schedule>> get() = _date

    init {
        fetchAllDoctors()
        //addDoctorDetails()
        _date.value = listOf(
            Schedule("Sunday,12 June","9.30AM-10.00AM"),
            Schedule("Friday,20 June","2.30PM-3.00PM"),
            Schedule("Tuesday,15 June","9.30AM-10.00AM"),
            Schedule("Monday,14 June","3.00PM-3.30PM")
        )
    }

    private fun fetchAllDoctors() {
        viewModelScope.launch {
            repository.getDoctors().collect { result ->
                _doctors.value = Resource.Success(result.data?.sortedBy { it.name } ?: emptyList())
                _maleDoctors.value = Resource.Success(result.data?.filter { it.gender == 0 } ?: emptyList())
                _femaleDoctors.value = Resource.Success(result.data?.filter { it.gender == 1 } ?: emptyList())
                Log.d("message", "->>>>>${result.data}")
            }
        }
    }

//    private fun addDoctorDetails(){
//        val doctors = listOf(
//            Doctor("2",  "Dr. Sophia Martinez, Ph.D.","sophia_martinez", "Cosmetic Bioengineering",23,"Focus: The impact of hormonal imbalances on skin conditions, specializing in acne, hirsutism, and other skin disorders.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", 9,17, 0, 7,4.8, 90),
//            Doctor("3",  "Dr. Olivia Turner, M.D.","olivia_turner", "Dermato-Endocrinology",23,"Focus: The impact of hormonal imbalances on skin conditions, specializing in acne, hirsutism, and other skin disorders.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", 9,17, 0, 7,4.8, 90),
//            Doctor("4",  "Dr. Michael Davidson, M.D.","micheal_davidson", "Solar Dermatology",23,"Focus: The impact of hormonal imbalances on skin conditions, specializing in acne, hirsutism, and other skin disorders.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", 9,17, 0, 7,4.8, 90)
//        )
//        for (doctor in doctors){
//            repository.addDoctor(doctor)
//        }
//    }
}
