package com.medicalhealth.healthapplication.model.data

sealed class AppointmentItem {
    data class Complete(val doctor: Doctor) : AppointmentItem()
    data class Upcoming(val upcoming: UpcomingAppointment):AppointmentItem()
    data class Cancelled(val doctor: Doctor):AppointmentItem()
}