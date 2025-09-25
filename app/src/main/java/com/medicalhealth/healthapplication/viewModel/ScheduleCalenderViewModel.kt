package com.medicalhealth.healthapplication.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.medicalhealth.healthapplication.model.data.Date
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.TimeSlot
import com.medicalhealth.healthapplication.model.repository.doctorBooking.BookingRepository
import com.medicalhealth.healthapplication.model.repository.doctorBooking.BookingRepositoryImpl
import com.medicalhealth.healthapplication.utils.Resource
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleCalenderViewModel : ViewModel() {

    private val bookingRepository: BookingRepository =
        BookingRepositoryImpl(FirebaseFirestore.getInstance())

    private val _dateList = MutableLiveData<List<Date>>()
    val dateList: LiveData<List<Date>> get() = _dateList

    private val _timeSlots = MutableLiveData<List<TimeSlot>>()
    val timeSlot: LiveData<List<TimeSlot>> get() = _timeSlots
    private val dayNames = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    private val _selectedTimeSlot = MutableLiveData<TimeSlot?>()
    val selectedTimeSlot: LiveData<TimeSlot?> get() = _selectedTimeSlot
    private val _bookingStatus = MutableLiveData<Resource<Boolean>>()
    val bookingStatus: LiveData<Resource<Boolean>> get() = _bookingStatus
    private val _availabilityStatus = MutableLiveData<Resource<List<String>>>()
    val availabilityStatus: LiveData<Resource<List<String>>> get() = _availabilityStatus
    private val _userBookings = MutableLiveData<Resource<List<Appointment>>>()
    val userBookings: LiveData<Resource<List<Appointment>>> get() = _userBookings

    private val _selectedDate = MutableLiveData<Date?>()
    val selectedDate: LiveData<Date?> get() = _selectedDate
    private var currentDoctorId: String = ""
    private var currentMonth: Int = 0

    init {
        _dateList.value = mutableListOf()
    }

    fun generateMonthDates(monthIndex: Int) {
        currentMonth = monthIndex

        val newDateList = mutableListOf<Date>()
        val today = java.util.Calendar.getInstance()
        val currentYear = today.get(java.util.Calendar.YEAR)
        val todayMonth = today.get(java.util.Calendar.MONTH)
        val currentDay = today.get(java.util.Calendar.DAY_OF_MONTH)
        val calendar = java.util.Calendar.getInstance()

        calendar.set(java.util.Calendar.YEAR, currentYear)
        calendar.set(java.util.Calendar.MONTH, monthIndex)
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
        for (day in 1..daysInMonth) {
            calendar.set(java.util.Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
            val isToday = (monthIndex == todayMonth && day == currentDay)
            val date = Date(
                dayOfMonth = day.toString(),
                dayOfWeek = dayNames[dayOfWeek - 1],
                isSelected = false,
                isToday = isToday
            )
            newDateList.add(date)
        }
        _dateList.value = newDateList
    }

    fun generateTimeSlots() {
        val newTimeSlots = mutableListOf<TimeSlot>()
        var time = LocalTime.of(9, 0)
        while (time.isBefore(LocalTime.of(16, 1))) {
            newTimeSlots.add(TimeSlot(time))
            time = time.plusMinutes(30)
        }
        _timeSlots.value = newTimeSlots
    }

    fun selectTimeSlot(time: LocalTime) {
        val selectedSlot = _timeSlots.value?.find { it.time == time }

        if (selectedSlot?.isAvailable == true) {
            _selectedTimeSlot.value = selectedSlot
            val updatedSlots = _timeSlots.value?.map { slot ->
                slot.copy(isSelected = slot.time == time)
            } ?: emptyList()
            _timeSlots.value = updatedSlots
        }
    }
    fun setCurrentDoctor(doctorId: String) {
        currentDoctorId = doctorId
    }

    fun onDateSelected(selectedDate: Date) {
        _selectedDate.value = selectedDate

        val updatedDates = _dateList.value?.map { date ->
            date.copy(isSelected = date.dayOfMonth == selectedDate.dayOfMonth)
        } ?: emptyList()
        _dateList.value = updatedDates

        checkSlotAvailability(selectedDate)

    }

    private fun checkSlotAvailability(selectedDate: Date) {

        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = currentMonth + 1
            val day = selectedDate.dayOfMonth.padStart(2, '0')
            val formattedDate = "$year-${month.toString().padStart(2, '0')}-$day"

            bookingRepository.getBookedSlots(currentDoctorId, formattedDate).collect { resource ->
                _availabilityStatus.value = resource

                when (resource) {
                    is Resource.Success -> {
                        val bookedTimes = resource.data ?: emptyList()
                        updateTimeSlotAvailability(bookedTimes)
                    }

                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                    }
                }
            }
        }
    }

    private fun updateTimeSlotAvailability(bookedTimes: List<String>) {

        val updatedSlots = _timeSlots.value?.map { slot ->
            slot.copy(
                isAvailable = slot.timeString !in bookedTimes,
                isSelected = false
            )
        } ?: emptyList()
        _timeSlots.value = updatedSlots
    }

    fun createBooking(
        patientName: String,
        patientAge: Int,
        patientGender: String,
        problemDescription: String,
        userId: String,
        personType: String
    ):Appointment? {
        val selectedDate = _selectedDate.value
        val selectedSlot = _selectedTimeSlot.value

        if (selectedDate == null || selectedSlot == null) {
            _bookingStatus.value = Resource.Error("Please select date and time")
            return null
        }

        if (!selectedSlot.isAvailable) {
            _bookingStatus.value = Resource.Error("Selected time slot is no longer available")
            return null
        }

        if (patientName.isBlank()) {
            _bookingStatus.value = Resource.Error("Please enter patient name")
            return null
        }

        if (patientAge <= 0) {
            _bookingStatus.value = Resource.Error("Please enter valid age")
            return null
        }

        if (patientGender.isBlank()) {
            _bookingStatus.value = Resource.Error("Please select gender")
            return null
        }

        if (currentDoctorId.isEmpty()) {
            _bookingStatus.value = Resource.Error("Doctor information missing")
            return null
        }


        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = currentMonth + 1
        val day = selectedDate.dayOfMonth.padStart(2, '0')
        val formattedDate = "$year-${month.toString().padStart(2, '0')}-$day"

        val booking = Appointment(
            userId = userId,
            doctorId = currentDoctorId,
            patientFullName = patientName,
            patientAge = patientAge,
            patientGender = patientGender,
            problemDescription = problemDescription,
            bookingDate = formattedDate,
            personType =personType,
            bookingTime = selectedSlot.timeString,
            status = "UPCOMING"
        )
        _bookingStatus.value = Resource.Success(true)
        return booking
    }
}





