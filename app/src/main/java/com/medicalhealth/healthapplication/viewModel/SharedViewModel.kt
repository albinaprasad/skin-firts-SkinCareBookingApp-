package com.medicalhealth.healthapplication.viewModel

import android.util.Log
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
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.repository.AppointmentRepository
import com.medicalhealth.healthapplication.model.repository.doctorBooking.BookingRepository
import com.medicalhealth.healthapplication.model.repository.doctorBooking.BookingRepositoryImpl
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.scheduleScreen.ScheduleDetailsActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.Typography.dagger

@HiltViewModel
class SharedViewModel @Inject constructor( private val bookingRepository : BookingRepository): ViewModel() {


    private val appointmentRepository:AppointmentRepository =
        AppointmentRepository(FirebaseFirestore.getInstance())


    private val _selectedDoctor = MutableLiveData<Doctor>()
    val selectedDoctor: LiveData<Doctor> = _selectedDoctor

    private val _titleChange = MutableLiveData<String>()
    val titleChange:LiveData<String> = _titleChange

    private var lastRequestedStatus: String = "COMPLETED"

    private val _appointmentList = MutableStateFlow<Resource<List<Appointment>>>(Resource.Loading())
    val appointmentList:StateFlow<Resource<List<Appointment>>> = _appointmentList


    private val _doctorList = MutableStateFlow<Resource<List<Doctor>>>(Resource.Loading())
    val doctorList:StateFlow<Resource<List<Doctor>>> = _doctorList


    private val _filteredAppointments = MutableLiveData<Resource<List<Appointment>>>()
    val filteredAppointments: LiveData<Resource<List<Appointment>>> = _filteredAppointments

    private val _statusUpdateResult = MutableLiveData<Resource<Unit>>(Resource.Success(Unit))
    val statusUpdateResult: LiveData<Resource<Unit>> = _statusUpdateResult





    fun selectDoctor(doctor: Doctor) {
        _selectedDoctor.value = doctor
    }

    fun setTitle(title:String){
        _titleChange.value = title
    }

    init{
        viewModelScope.launch {
            fetchTheDoctor()
            fetchTheAppointment()
        }
    }

    fun changeTheStatus(documentId:String,status:String){
        viewModelScope.launch {
            _statusUpdateResult.value = Resource.Loading()
            appointmentRepository.ChangeTheStatus(documentId,status).collect{ resource ->
                _statusUpdateResult.value = resource

                if (resource is Resource.Success) {
                    fetchTheAppointment()
                }
            }
        }

    }

     suspend fun fetchTheAppointment() {
        appointmentRepository.fetchAllAppointments().collect{ result ->
            _appointmentList.value = result
            Log.d("message", "All Appointments: ${result.data?.size}")

            // ⭐ Automatically filter for the initial status ("COMPLETED") after first fetch
            if (result is Resource.Success && result.data != null) {
                filterAppointmentsByStatus(lastRequestedStatus)
            }
        }
    }

    private suspend fun fetchTheDoctor(){
        appointmentRepository.fetchTheDoctor().collect{doctor ->
            _doctorList.value = doctor
            Log.d("message", "All Doctors: ${doctor.data?.size}")
        }
    }

    fun filterAppointmentsByStatus(status:String){
        lastRequestedStatus = status
        // ⭐ FIX for NullPointerException and incorrect filtering logic
        val allAppointmentsResource = _appointmentList.value

        if (allAppointmentsResource is Resource.Success && allAppointmentsResource.data != null) {
            // Correctly filter the list by the desired status
            val filtered = allAppointmentsResource.data.filter { it.status == status }
            // Now, enrich the filtered list with doctor details before publishing
            val appointmentsWithDetails = getAndReplaceDoctorDetails(filtered)
            _filteredAppointments.value = Resource.Success(appointmentsWithDetails)
        } else {
            // Handle loading or error state gracefully
            _filteredAppointments.value = Resource.Loading()
        }
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

    fun getDoctorDetails(): List<Doctor>? {
        return doctorList.value.data
    }

    private fun getAndReplaceDoctorDetails(appointments: List<Appointment>): List<Appointment>{
        val doctorList = getDoctorDetails()
        val mutableAppointments = appointments.toMutableList()

        mutableAppointments.forEach { appointment ->
            if (doctorList != null) {
                val doctor = doctorList.find { it.id == appointment.doctorId }
                appointment.doctorName = doctor?.name ?: ""
                appointment.doctorSpec = doctor?.specialization ?: ""
                appointment.doctorPic = doctor?.profileImageUrl ?: ""
                appointment.rating = doctor?.rating ?: 0.0
            }
        }
        return mutableAppointments
    }

}
