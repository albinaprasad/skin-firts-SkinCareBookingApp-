package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.Date
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.repository.DoctorDetailsRepository
import com.medicalhealth.healthapplication.model.repository.DoctorDetailsRepositoryImpl
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: DoctorDetailsRepository = DoctorDetailsRepositoryImpl(
        FirebaseFirestore.getInstance()
    )
) : ViewModel() {
    private val _dates = MutableLiveData<List<Date>?>()
    val dates: MutableLiveData<List<Date>?> get() = _dates

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointment: LiveData<List<Appointment>> get() = _appointments

    private val _dummyDoctors = MutableLiveData<List<Doctor>>()
    val dummyDoctors: LiveData<List<Doctor>> get() = _dummyDoctors

    private val _doctors = MutableStateFlow<Resource<List<Doctor>>>(Resource.Loading())
    val doctors: StateFlow<Resource<List<Doctor>>> = _doctors

    init {
        fetchAllDoctors()
        _dates.value = listOf(
            Date("9", "MON", false),
            Date("10", "TUE", false),
            Date("11", "WED", true),
            Date("12", "THU", false),
            Date("13", "FRI", true),
            Date("14", "SAT", true),
        )
        _appointments.value = listOf(
            Appointment(
                "1",
                "10 AM",
                "Dr. Olivia Turner, M.D.",
                "Treatment and prevention of skin and photodermatitis."
            ),
            Appointment(
                "1",
                "10 AM",
                "Dr. Olivia Turner, M.D.",
                "Treatment and prevention of skin and photodermatitis."
            ),
            Appointment(
                "1",
                "10 AM",
                "Dr. Olivia Turner, M.D.",
                "Treatment and prevention of skin and photodermatitis."
            ),
            Appointment(
                "2",
                "10 AM",
                "Dr. Abc Turner, M.D.",
                "Treatment and prevention of skin and photodermatitis."
            ),
            Appointment(
                "2",
                "10 AM",
                "Dr. Abc Turner, M.D.",
                "Treatment and prevention of skin and photodermatitis."
            )
        )

        _dummyDoctors.value = listOf(
            Doctor(
                "1",
                "Dr. Olivia Turner, M.D.",
                "alexander_bennett",
                "Dermato-Endocrinology",
                15,
                0,
                "",
                "",
                "",
                "",
                9,
                17,
                17,
                0,
                5.0,
                40
            ),
            Doctor(
                "2",
                "Dr. Alexander Bennett, Ph.D.",
                "alexander_bennett",
                "Dermato-Genetics",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                0,
                7,
                4.5,
                40
            ),
            Doctor(
                "3",
                "Dr. Sophia Martinez, Ph.D.",
                "alexander_bennett",
                "Cosmetic Bioengineering",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                0,
                7,
                5.0,
                150
            ),
            Doctor(
                "4",
                "Dr. Michael Davidson, M.D.",
                "alexander_bennett",
                "Nano-Dermatology",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                0,
                7,
                4.8,
                90
            ),
            Doctor(
                "4",
                "Dr. Michael Davidson, M.D.",
                "alexander_bennett",
                "Nano-Dermatology",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                0,
                7,
                4.8,
                90
            ),
            Doctor(
                "4",
                "Dr. Michael Davidson, M.D.",
                "alexander_bennett",
                "Nano-Dermatology",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                0,
                7,
                4.8,
                90
            ),
            Doctor(
                "4",
                "Dr. Michael Davidson, M.D.",
                "alexander_bennett",
                "Nano-Dermatology",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                0,
                7,
                4.8,
                90
            )
        )
    }

    fun selectDate(selectedDate: Date) {
        val updatedList = _dates.value?.map { date ->
            date.isSelected = (date == selectedDate)
            date
        }
        _dates.value = updatedList!!
    }

    fun toggleFavoriteStatus(doctorId: String) {

    }

    private fun fetchAllDoctors() {
        viewModelScope.launch {
            repository.getDoctors().collect{
                _doctors.value = it
            }
        }
    }
}