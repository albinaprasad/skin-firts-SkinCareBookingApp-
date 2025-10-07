package com.medicalhealth.healthapplication.model.data

sealed class AppointmentItem {
    data class Complete(val doctor:List<Appointment>) : AppointmentItem()
    data class Upcoming(val upcoming: List<Appointment>):AppointmentItem()
    data class Cancelled(val doctor: List<Appointment>):AppointmentItem()
}