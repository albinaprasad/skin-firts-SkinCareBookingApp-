package com.medicalhealth.healthapplication.viewModel

import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.AppointmentItem
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.repository.doctorBooking.BookingRepository
import com.medicalhealth.healthapplication.model.repository.doctorBooking.BookingRepositoryImpl
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.scheduleScreen.ScheduleDetailsActivity
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {


    private val bookingRepository: BookingRepository =
        BookingRepositoryImpl(FirebaseFirestore.getInstance())

    private val _selectedDoctor = MutableLiveData<Doctor>()
    val selectedDoctor: LiveData<Doctor> = _selectedDoctor

    private val _currentDataList = MutableLiveData<List<Any>>()
    val currentDataList: LiveData<List<Any>> = _currentDataList

    private val _titleChange = MutableLiveData<String>()
    val titleChange:LiveData<String> = _titleChange

    fun selectDoctor(doctor: Doctor) {
        _selectedDoctor.value = doctor
    }

    fun setTitle(title:String){
        _titleChange.value = title
    }

    fun setData(data: List<AppointmentItem>) {
        _currentDataList.value = data
    }

    fun updateButtons(
        fragmentName: String,
        ratingBtn: ImageButton,
        sortButton: Button,
        favBtn: ImageButton,
        femaleBtn: ImageButton,
        maleBtn: ImageButton
    ) {
        resetAllButtons(ratingBtn, sortButton, favBtn, femaleBtn, maleBtn)

        when (fragmentName) {
            "RatingFragment" -> {
                ratingBtn.setImageResource(R.drawable.star_icon_white)
                ratingBtn.setBackgroundResource(R.drawable.background_ellipse_blue)
            }

            "DoctorListFragment" -> {
                sortButton.backgroundTintList =
                    ContextCompat.getColorStateList(sortButton.context, R.color.button_blue)
                sortButton.setTextColor(ContextCompat.getColor(sortButton.context, R.color.white))
            }

            "FavouriteFragment" -> {
                favBtn.setImageResource(R.drawable.fav_white_filled)
                favBtn.setBackgroundResource(R.drawable.background_ellipse_blue)
            }

            "MaleListFragment" -> {
                maleBtn.setImageResource(R.drawable.male_icon_white)
                maleBtn.setBackgroundResource(R.drawable.background_ellipse_blue)
            }

            "FemaleListFragment" -> {
                femaleBtn.setImageResource(R.drawable.female_icon_white)
                femaleBtn.setBackgroundResource(R.drawable.background_ellipse_blue)
            }
        }
    }

    fun resetAllButtons(
        ratingBtn: ImageButton, sortButton: Button, favBtn: ImageButton, femaleBtn: ImageButton, maleBtn: ImageButton
    ) {
        sortButton.backgroundTintList =
            ContextCompat.getColorStateList(sortButton.context, R.color.off_blue)
        sortButton.setTextColor(ContextCompat.getColor(sortButton.context, R.color.button_blue))
        ratingBtn.setImageResource(R.drawable.star_icon)
        ratingBtn.setBackgroundResource(R.drawable.round_button_offblue)
        favBtn.setImageResource(R.drawable.fav_icon)
        favBtn.setBackgroundResource(R.drawable.round_button_offblue)
        femaleBtn.setImageResource(R.drawable.female_icon)
        femaleBtn.setBackgroundResource(R.drawable.round_button_offblue)
        maleBtn.setImageResource(R.drawable.male_icon)
        maleBtn.setBackgroundResource(R.drawable.round_button_offblue)
    }

    fun formatSchedule(startDay: Int, endDay: Int, startTime: Int, endTime: Int): String{
        val formattedStartTime = convertToTwelveHourFormat(startTime)
        val formattedEndTime = convertToTwelveHourFormat(endTime)
        val startDayOfWeek = mapIntToDayOfWeek(startDay)
        val endDayOfWeek = mapIntToDayOfWeek(endDay)
        return "$startDayOfWeek - $endDayOfWeek / $formattedStartTime - $formattedEndTime"
    }

    private fun mapIntToDayOfWeek(index: Int): String{
        return when(index){
            0 -> "Sun"
            1 -> "Mon"
            2 -> "Tue"
            3 -> "Wed"
            4 -> "Thu"
            5 -> "Fri"
            6 -> "Sat"
            else -> {"N/A"}
        }
    }

    private fun convertToTwelveHourFormat(timeIn24Hour: Int): String{
        require(timeIn24Hour in 0..23) {"Time must be between 0 and 23."}
        val hour = timeIn24Hour % 12
        val amPm = if(timeIn24Hour >= 12) "PM" else "AM"
        val formattedHour = if(hour == 0) 12 else hour
        return "$formattedHour:00$amPm"

    }

    fun confrimBooking(booking: Appointment, context: ScheduleDetailsActivity) {
        viewModelScope.launch {
            bookingRepository.createBooking(booking).collect { resource ->
                when(resource)
                {
                    is Resource.Success->{
                        Toast.makeText(context, context.getString(R.string.booking_sucess), Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error<*> -> {
                        Toast.makeText(context,context.getString(R.string.booking_failed) , Toast.LENGTH_SHORT).show()
                    }
                    else -> {

                    }
                }
            }
        }
    }
}
