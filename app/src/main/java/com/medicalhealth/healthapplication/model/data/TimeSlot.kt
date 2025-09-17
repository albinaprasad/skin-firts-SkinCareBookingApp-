package com.medicalhealth.healthapplication.model.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime

data class TimeSlot(val time: LocalTime,
    var isSelected: Boolean=false)
{
    @RequiresApi(Build.VERSION_CODES.O)
    var timeString:String=time.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"))
}
