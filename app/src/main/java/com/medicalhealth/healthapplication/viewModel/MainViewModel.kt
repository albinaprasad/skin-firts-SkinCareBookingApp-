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

    private val _doctors = MutableStateFlow<Resource<List<Doctor>>>(Resource.Loading())
    val doctors: StateFlow<Resource<List<Doctor>>> = _doctors

    private val dayNames = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    init {
        fetchAllDoctors()
        _dates.value = mutableListOf()
        generateMonthDates()
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

    private fun generateMonthDates(){
        val newDateList = mutableListOf<Date>()
        val today = java.util.Calendar.getInstance()
        val currentDay = today.get(java.util.Calendar.DAY_OF_MONTH)
        val daysInMonth = today.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
        for(day in 1..daysInMonth){
            today.set(java.util.Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = today.get(java.util.Calendar.DAY_OF_WEEK)
            val isToday = (day == currentDay)
            val date = Date(day.toString(), dayNames[dayOfWeek - 1], false, isToday)
            newDateList.add(date)
        }
        _dates.value = newDateList
    }
}