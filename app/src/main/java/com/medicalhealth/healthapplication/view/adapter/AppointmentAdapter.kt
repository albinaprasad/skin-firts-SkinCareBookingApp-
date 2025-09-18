package com.medicalhealth.healthapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.CancelledAppointmentBinding
import com.medicalhealth.healthapplication.databinding.CompleteAppointmentBinding
import com.medicalhealth.healthapplication.databinding.UpcomingAppointmentBinding
import com.medicalhealth.healthapplication.model.data.AppointmentItem
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.data.UpcomingAppointment

class AppointmentAdapter(
    private val context: Context,
    private var doctorList: List<AppointmentItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_COMPLETE = 1
    private val VIEW_TYPE_UPCOMING = 2
    private val VIEW_TYPE_CANCELLED = 3

    fun updateChanges(newList: List<AppointmentItem>) {
        doctorList = newList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {

        return when (doctorList[position]) {
            is AppointmentItem.Complete -> VIEW_TYPE_COMPLETE
            is AppointmentItem.Upcoming-> VIEW_TYPE_UPCOMING
            is AppointmentItem.Cancelled -> VIEW_TYPE_CANCELLED

            else -> throw IllegalArgumentException("Invalid view type at position $position")


        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_COMPLETE -> {
                val binding = CompleteAppointmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CompleteAppointmentViewHolder(binding)

            }

            VIEW_TYPE_UPCOMING -> {
                val binding = UpcomingAppointmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                UpcomingAppointmentViewHolder(binding)

            }
            VIEW_TYPE_CANCELLED -> {
                val binding = CancelledAppointmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CancelledAppointmentViewHolder(binding)

            }

            else -> throw IllegalArgumentException("Invalid view type")

        }


    }






    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = doctorList[position]) {
            is AppointmentItem.Complete -> {

                (holder as CompleteAppointmentViewHolder).bind(item.doctor, context)
            }

            is AppointmentItem.Upcoming -> {
                (holder as UpcomingAppointmentViewHolder).bind(item.upcoming, context)
            }
            is AppointmentItem.Cancelled -> {

                (holder as CancelledAppointmentViewHolder).bind(item.doctor, context)
            }

        }


    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

}

class CompleteAppointmentViewHolder(val binding: CompleteAppointmentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(appointment: Doctor, context: Context) {
        binding.ivDoctorPic.setImageResource(appointment.profileImageUrl)
        binding.tvDoctorName.text = appointment.name
        binding.tvSpecialization.text = appointment.specialization
        binding.tvDoctorRating.text = appointment.rating.toString()
        binding.tvRebook.setOnClickListener {
            Toast.makeText(context, "Rebooking..", Toast.LENGTH_SHORT).show()
        }
        binding.tvAddReview.setOnClickListener {
            Toast.makeText(context, "Adding review...", Toast.LENGTH_SHORT).show()
        }

    }

}
class CancelledAppointmentViewHolder(val binding: CancelledAppointmentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cancelled: Doctor, context: Context) {
        binding.tvDoctorNameCancelled.text = cancelled.name
        binding.tvSpecializationCancelled.text = cancelled.specialization
        binding.ivDoctorPicCancelled.setImageResource(cancelled.profileImageUrl)
        binding.btnAddReview.setOnClickListener {
            Toast.makeText(context, "Adding...", Toast.LENGTH_SHORT).show()
        }

    }
}

    class UpcomingAppointmentViewHolder(val binding: UpcomingAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(upcoming: UpcomingAppointment, context: Context) {
            binding.ivDoctorPic.setImageResource(upcoming.doctor.profileImageUrl)
            binding.tvDoctorName.text = upcoming.doctor.name
            binding.tvSpecialization.text = upcoming.doctor.specialization
            binding.tvDateOfAppointment.text = upcoming.schedule.day
            binding.tvTimeOfAppointment.text = upcoming.schedule.time
            binding.cvDetails.setOnClickListener {
                Toast.makeText(context, "Details...", Toast.LENGTH_SHORT).show()
            }

        }

    }


