package com.medicalhealth.healthapplication.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicalhealth.healthapplication.model.data.Date
import com.medicalhealth.healthapplication.model.data.DoctorBooking
import com.medicalhealth.healthapplication.model.data.TimeSlot
import com.medicalhealth.healthapplication.utils.Resource
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleCalenderViewModel: ViewModel() {
     private val _dateList = MutableLiveData<List<Date>>()
    val dateList: LiveData<List<Date>> get() = _dateList

    private val _timeSlots = MutableLiveData<List<TimeSlot>>()
    val timeSlot: LiveData<List<TimeSlot>> get() = _timeSlots
    private val dayNames = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    private val _bookingStatus = MutableLiveData<Resource<Boolean>>()
    val bookingStatus: LiveData<Resource<Boolean>> get() = _bookingStatus
    private val _availabilityStatus = MutableLiveData<Resource<List<String>>>()
    val availabilityStatus: LiveData<Resource<List<String>>> get() = _availabilityStatus
    private val _userBookings = MutableLiveData<Resource<List<DoctorBooking>>>()
    val userBookings: LiveData<Resource<List<DoctorBooking>>> get() = _userBookings


    init {
        _dateList.value=mutableListOf()
    }
    fun generateMonthDates(monthIndex:Int)
    {
        val newDateList = mutableListOf<Date>()
        val today = java.util.Calendar.getInstance()
        val currentYear = today.get(java.util.Calendar.YEAR)
        val currentMonth = today.get(java.util.Calendar.MONTH)
        val currentDay = today.get(java.util.Calendar.DAY_OF_MONTH)
        val calendar = java.util.Calendar.getInstance()

        calendar.set(java.util.Calendar.YEAR, currentYear)
        calendar.set(java.util.Calendar.MONTH, monthIndex)
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
        for(day in 1..daysInMonth)
        {
            calendar.set(java.util.Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
            val isToday = (monthIndex == currentMonth && day == currentDay)
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

    fun generateTimeSlots(){
        val newTimeSlots = mutableListOf<TimeSlot>()
        var time = LocalTime.of(9, 0)
        while (time.isBefore(LocalTime.of(16, 1))) {
            newTimeSlots.add(TimeSlot(time))
            time = time.plusMinutes(30)
        }
        _timeSlots.value = newTimeSlots
    }

    fun selectTimeSlot(time: LocalTime) {
        val updatedSlots = _timeSlots.value?.map { slot ->
            slot.copy(isSelected = slot.time == time)
        } ?: emptyList()
        _timeSlots.value = updatedSlots
    }
}