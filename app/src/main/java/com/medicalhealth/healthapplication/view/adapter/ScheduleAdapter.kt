package com.medicalhealth.healthapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.ItemScheduleAppointmentBinding
import com.medicalhealth.healthapplication.model.data.Appointment

class ScheduleAdapter(private var appointments: List<Appointment>) :
    RecyclerView.Adapter<ScheduleAdapter.AppointmentViewHolder>() {
    class AppointmentViewHolder(val binding: ItemScheduleAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleAdapter.AppointmentViewHolder {
        val binding = ItemScheduleAppointmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleAdapter.AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        with(holder.binding){
            startTimeTextView.text = appointment.bookingTime
            endTimeTextView.text = appointment.endTime
            doctorNameTextView.text = appointment.doctorName
            treatmentTextView.text = appointment.problemDescription
        }
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    fun updateData(newAppointmentList: List<Appointment>){
        appointments = newAppointmentList
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): Appointment{
        return appointments[position]
    }


}