package com.medicalhealth.healthapplication.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.repository.DoctorDetailsRepository
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.medicalhealth.healthapplication.model.data.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DoctorsListViewModel @Inject constructor(
    private val repository: DoctorDetailsRepository) : ViewModel() {
    private val _doctors = MutableStateFlow<Resource<List<Doctor>>>(
        value = Resource.Loading()
    )
    val doctors: StateFlow<Resource<List<Doctor>>> = _doctors

    private var allDoctors: List<Doctor>? = null

    private val _date = MutableLiveData<List<Schedule>>()
    val date: LiveData<List<Schedule>> get() = _date

    init {
        //addDoctorDetails()
        _date.value = listOf(
            Schedule("Sunday,12 June", "9.30AM-10.00AM"),
            Schedule("Friday,20 June", "2.30PM-3.00PM"),
            Schedule("Tuesday,15 June", "9.30AM-10.00AM"),
            Schedule("Monday,14 June", "3.00PM-3.30PM")
        )
    }

     fun loadDoctors(filterType: String) {
         if (allDoctors != null) {
             filterAndEmit(allDoctors!!, filterType)
         } else {
             viewModelScope.launch {
                 repository.getDoctors().collect { result ->
                     if (result is Resource.Success) {
                         allDoctors = result.data?.sortedBy { it.name }
                         filterAndEmit(allDoctors!!, filterType)
                         Log.d("message", "All doctors fetched: ${allDoctors?.size}")
                     } else {
                         _doctors.value = result
                     }
                 }
             }
         }
     }

    private fun filterAndEmit(doctorsList: List<Doctor>, filterType: String) {
        val filteredList = when (filterType) {
            "Male" -> doctorsList.filter { it.gender == 0 }
            "Female" -> doctorsList.filter { it.gender == 1 }
            else -> doctorsList
        }
        _doctors.value = Resource.Success(filteredList)
        Log.d("message", "Filtered list for $filterType: ${filteredList.size}")
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
