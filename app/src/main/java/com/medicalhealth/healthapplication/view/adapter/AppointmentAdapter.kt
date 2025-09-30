package com.medicalhealth.healthapplication.view.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.CancelledAppointmentBinding
import com.medicalhealth.healthapplication.databinding.CompleteAppointmentBinding
import com.medicalhealth.healthapplication.databinding.UpcomingAppointmentBinding
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.AppointmentItem
import com.medicalhealth.healthapplication.utils.utils.addThirtyMinutes
import com.medicalhealth.healthapplication.utils.utils.convertDateToLongFormat
import com.medicalhealth.healthapplication.view.CancelAppointment
import com.medicalhealth.healthapplication.view.homeScreen.MainActivity
import com.squareup.picasso.Picasso


class AppointmentAdapter(
    private val context: Context,
    // This list now contains a wrapper for each individual appointment
    private var appointmentItems: List<AppointmentItem>,
    private val onCancelClick: (String) -> Unit, // For btnCross
    private val onCompleteClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Constants for View Types
    private val VIEW_TYPE_COMPLETE = 1
    private val VIEW_TYPE_UPCOMING = 2
    private val VIEW_TYPE_CANCELLED = 3


    fun updateChanges(newList: List<AppointmentItem>) {
        appointmentItems = newList
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return when (appointmentItems[position]) {
            is AppointmentItem.Complete -> VIEW_TYPE_COMPLETE
            is AppointmentItem.Upcoming -> VIEW_TYPE_UPCOMING
            is AppointmentItem.Cancelled -> VIEW_TYPE_CANCELLED
            else -> throw IllegalArgumentException("Invalid view type at position $position")
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        // Inflate the correct layout based on the viewType
        return when (viewType) {
            VIEW_TYPE_COMPLETE -> {
                val binding = CompleteAppointmentBinding.inflate(inflater, parent, false)
                CompleteAppointmentViewHolder(binding)
            }
            VIEW_TYPE_UPCOMING -> {
                val binding = UpcomingAppointmentBinding.inflate(inflater, parent, false)
                UpcomingAppointmentViewHolder(binding,onCancelClick,onCompleteClick)
            }
            VIEW_TYPE_CANCELLED -> {
                val binding = CancelledAppointmentBinding.inflate(inflater, parent, false)
                CancelledAppointmentViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Get the specific AppointmentItem wrapper
        when (val item = appointmentItems[position]) {
            is AppointmentItem.Complete -> {
                // Safely extract the single Appointment from the list inside the wrapper
                if (item.doctor.isNotEmpty()) {
                    (holder as CompleteAppointmentViewHolder).bind(item.doctor[0], context)
                }
            }

            is AppointmentItem.Upcoming -> {
                if (item.upcoming.isNotEmpty()) {
                    (holder as UpcomingAppointmentViewHolder).bind(item.upcoming[0], context)
                }
            }

            is AppointmentItem.Cancelled -> {
                if (item.doctor.isNotEmpty()) {
                    (holder as CancelledAppointmentViewHolder).bind(item.doctor[0], context)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        // The count is simply the size of the list of wrappers
        return appointmentItems.size
    }

    // --- ViewHolder Definitions ---

    class CompleteAppointmentViewHolder(val binding: CompleteAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appointment: Appointment, context: Context) {
            with(binding){
                // Assuming doctorPic contains the image file name or URL
                Picasso.get()
                    .load("file:///android_asset/doctor_images/${appointment.doctorPic}.png")
                    .into(ivDoctorPic)
                tvDoctorName.text = appointment.doctorName
                tvSpecialization.text = appointment.doctorSpec
                tvDoctorRating.text = appointment.rating.toString()
                tvRebook.setOnClickListener {
                    Toast.makeText(context, "Rebooking..", Toast.LENGTH_SHORT).show()
                }
                tvAddReview.setOnClickListener {
                    (context as? MainActivity)?.startReviewActivity() // Assuming MainActivity has this method
                }
            }
        }
    }

    class CancelledAppointmentViewHolder(val binding: CancelledAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cancelled: Appointment, context: Context) {
            with(binding) {
                Picasso.get()
                    .load("file:///android_asset/doctor_images/${cancelled.doctorPic}.png")
                    .into(ivDoctorPicCancelled)
                tvDoctorNameCancelled.text = cancelled.doctorName
                tvSpecializationCancelled.text = cancelled.doctorSpec
                btnAddReview.setOnClickListener {
                    (context as? MainActivity)?.startReviewActivity() // Assuming MainActivity has this method
                }
            }
        }
    }

    class UpcomingAppointmentViewHolder(val binding: UpcomingAppointmentBinding,private val onCancelClick: (String) -> Unit,
                                        private val onCompleteClick: (String) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(upcoming: Appointment, context: Context) {
            with(binding) {
                Picasso.get()
                    .load("file:///android_asset/doctor_images/${upcoming.doctorPic}.png")
                    .into(ivDoctorPic)
                tvDoctorName.text = upcoming.doctorName
                Log.d("mathews", "bind: ${upcoming.doctorName},${upcoming.doctorSpec},${upcoming.bookingDate}")
                tvSpecialization.text = upcoming.doctorSpec
                tvDateOfAppointment.text = convertDateToLongFormat(upcoming.bookingDate)
                val endTime = addThirtyMinutes(upcoming.bookingTime)
                tvTimeOfAppointment.text = "${upcoming.bookingTime} - ${endTime}"
                btnCross.setOnClickListener {
                    onCancelClick(upcoming.bookingId)
                }
                btnTick.setOnClickListener {
//                    (context as? MainActivity)?.viewModel?.ChangeTheStatus(upcoming.bookingId,"COMPLETED")
                    onCompleteClick(upcoming.bookingId)
                }

                cvDetails.setOnClickListener {
                    Toast.makeText(context, "Details...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}