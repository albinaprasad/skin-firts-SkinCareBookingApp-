package com.medicalhealth.healthapplication.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.Date
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.repository.doctorDetailsRepository.DoctorDetailsRepository
import com.medicalhealth.healthapplication.model.data.Users
import com.medicalhealth.healthapplication.model.repository.authenticationRepository.AuthenticationRepositoryImpl
import com.medicalhealth.healthapplication.model.repository.doctorBooking.BookingRepository
import com.medicalhealth.healthapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DoctorDetailsRepository, private val bookingRepository: BookingRepository) : ViewModel() {


    private val authRepository: AuthenticationRepositoryImpl = AuthenticationRepositoryImpl()

    private val _currentUserDetails = MutableStateFlow<Resource<Users>>(Resource.Loading())
    val currentUserDetails: StateFlow<Resource<Users>> = _currentUserDetails

    private val _dates = MutableLiveData<List<Date>?>()
    val dates: MutableLiveData<List<Date>?> get() = _dates

    private val _appointments = MutableStateFlow<Resource<List<Appointment>>>(Resource.Loading())
    val appointments: StateFlow<Resource<List<Appointment>>> = _appointments

    private val _doctors = MutableStateFlow<Resource<List<Doctor>>>(Resource.Loading())
    val doctors: StateFlow<Resource<List<Doctor>>> = _doctors

    private val daysList: MutableList<Int> = mutableListOf()

    private val dayNames = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    init {
        viewModelScope.launch {
            fetchCurrentUserDetails()
            fetchAllDoctors()
            fetchBookingsForCurrentMonth()
        }
    }

    fun selectDate(selectedDate: Date) {

    }

    fun toggleFavoriteStatus(doctorId: String) {
        val currentResource = _currentUserDetails.value

        if(currentResource is Resource.Success){
            val user = currentResource.data ?: return

            val currentFavourite = user.favouriteDoctors
            val newFavourite = if(currentFavourite.contains(doctorId)){
                currentFavourite - doctorId
            }else{
                currentFavourite + doctorId
            }

            val updatedUser = user.copy(favouriteDoctors = newFavourite)
            _currentUserDetails.value = Resource.Success(updatedUser)

            viewModelScope.launch {
                val updatedResult = repository.updateFavoriteDoctors(user.uid, newFavourite)

                if(updatedResult is Resource.Error){
                    Log.d("message", "ViewModel issue: ${updatedResult.message}")
                }
            }
        }
    }

    private suspend fun fetchCurrentUserDetails(){
        authRepository.fetchCurrentUserDetails().collect{ result ->
            if(result is Resource.Success){
                _currentUserDetails.value = result
            }
        }
    }
    fun refreshCurrentUserDetails() {
        viewModelScope.launch {
            fetchCurrentUserDetails()
        }
    }

    private suspend fun fetchAllDoctors() {
        repository.getDoctors().collect {
            _doctors.value = it
        }
    }

    suspend fun fetchBookingsForCurrentMonth() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH) + 1
        val currentDay = calender.get(Calendar.DAY_OF_MONTH)
        val daysInMonth = calender.getActualMaximum(Calendar.DAY_OF_MONTH)
        val startOfMonth = String.format(Locale.US, "%04d-%02d-%02d", year, month, currentDay)
        val endOfMonth = String.format(Locale.US, "%04d-%02d-%02d", year, month, daysInMonth)
        bookingRepository.getBookingsForCurrentUserAndMonth(startOfMonth, endOfMonth)
            .collect { result ->
                if (result is Resource.Success && result.data != null) {
                    _appointments.value = Resource.Success(result.data.sortedBy { it.bookingDate })
                    getBookedDays(result.data)
                }
                generateMonthDates()
            }
    }

    private fun getBookedDays(appointments: List<Appointment>) {
        for (appointment in appointments) {
            getDayFromDateString(appointment.bookingDate)?.let { daysList.add(it) }
        }
    }

    private fun generateMonthDates() {
        val newDateList = mutableListOf<Date>()
        val today = java.util.Calendar.getInstance()
        var currentDay = today.get(java.util.Calendar.DAY_OF_MONTH)
//        today.set(Calendar.MONTH, 11)
        val daysInMonth = today.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
        currentDay = removePastDates(currentDay)
        for (day in currentDay..daysInMonth) {
            today.set(java.util.Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = today.get(java.util.Calendar.DAY_OF_WEEK)
            val isBooked = (day in daysList)
            val date = Date(day.toString(), dayNames[dayOfWeek - 1], isBooked, false)
            newDateList.add(date)
        }
        _dates.value = newDateList
    }

    private fun getDayFromDateString(dateString: String): Int? {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        return try {
            val date: java.util.Date? = format.parse(dateString)
            if (date != null) {
                val calendar = java.util.Calendar.getInstance()
                calendar.time = date
                return calendar.get(java.util.Calendar.DAY_OF_MONTH)
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    fun getDoctors(): List<Doctor> {
        return doctors.value.data ?: emptyList()
    }

    fun formatRelativeDate(context: Context?, dateString: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputFormatter = SimpleDateFormat("dd EEEE", Locale.getDefault())

        try {
            val inputDate: java.util.Date = inputFormatter.parse(dateString) ?: return "Invalid"

            val today = Calendar.getInstance()
            today.set(Calendar.HOUR_OF_DAY, 0)
            today.set(Calendar.MINUTE, 0)
            today.set(Calendar.SECOND, 0)
            today.set(Calendar.MILLISECOND, 0)

            val inputCal = Calendar.getInstance()
            inputCal.time = inputDate
            inputCal.set(Calendar.HOUR_OF_DAY, 0)
            inputCal.set(Calendar.MINUTE, 0)
            inputCal.set(Calendar.SECOND, 0)
            inputCal.set(Calendar.MILLISECOND, 0)

            val differenceInMilli = inputCal.timeInMillis - today.timeInMillis
            val daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMilli)

            val resourceId = when (daysDifference) {
                0L -> R.string.today
                1L -> R.string.tomorrow
                -1L -> R.string.yesterday
                in 2L..6L -> {
                    if(inputCal.get(Calendar.DAY_OF_WEEK) > today.get(Calendar.DAY_OF_WEEK)){
                        R.string.this_week
                    }else{
                        R.string.next_week
                    }
                }
                in 7L..13L -> R.string.next_week
                else -> 0
            }
            val formattedDate = outputFormatter.format(inputDate)
            return if (resourceId != 0) {
                "$formattedDate - ${context?.getString(resourceId)}"
            } else {
                formattedDate
            }
        } catch (e: Exception) {
            return "Parsing error"
        }
    }

    private fun removePastDates(currentDay: Int): Int{
        return if (currentDay in 4..24) {
            currentDay - 3
        }else if(currentDay > 25){
            25
        }else{
            currentDay
        }
    }

    fun keepMeSignedIn():FirebaseUser?{
        return authRepository.getCurrentUser()
    }

}