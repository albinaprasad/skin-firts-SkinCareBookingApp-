package com.medicalhealth.healthapplication.view.scheduleScreen

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.databinding.ActivityScheduleDetailsBinding
import com.medicalhealth.healthapplication.databinding.ItemDoctorProfileBinding
import com.medicalhealth.healthapplication.viewModel.SharedViewModel
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.utils.enums.Enums
import com.medicalhealth.healthapplication.utils.utils.getSystemBarInsets
import com.medicalhealth.healthapplication.view.BaseActivity
import java.util.Calendar

class ScheduleDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityScheduleDetailsBinding
    private val viewModel: SharedViewModel by viewModels()

    private lateinit var booking: Appointment
    private lateinit var doctor: Doctor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScheduleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            insets.getSystemBarInsets(v) {
                binding.root.setPadding(0, 0, 0, it.bottom)
            }
        }

        setupDoctorData()
        buttonClickListeners()
    }

    private fun setupDoctorData() {
        viewModel.selectedDoctor.observe(this) { selectedDoctor ->
            selectedDoctor?.let { doctor ->
                updateDoctorProfile(doctor)
            }
        }

         doctor =intent.getSerializableExtra("doctor_object") as Doctor
        booking=intent.getSerializableExtra("booking_object")as Appointment

        viewModel.selectDoctor(doctor)

    }

    private fun updateDoctorProfile(doctor: Doctor) {
        val doctorProfileBinding = ItemDoctorProfileBinding.inflate(layoutInflater)
        val inputStream = assets.open("doctor_images/${doctor.profileImageUrl}.png")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        with(doctorProfileBinding) {
            doctorProfilePicture.setImageBitmap(bitmap)
            doctorNameTextView.text = doctor.name
            doctorSpecializationTextView.text = doctor.specialization
            ratingTextView.text = doctor.rating.toString()
            commentCountTextView.text = "${doctor.commentCount}"
        }
        binding.doctorCardContainer.removeAllViews()
        binding.doctorCardContainer.addView(doctorProfileBinding.root)

        val (monthDay, dayName, year) = splitDayAndMonthFromDate()

        with(binding){

            dateBtn.text = monthDay+","+year
            dayAndTimeTextView.text= "$dayName,${booking.bookingTime}"

            bookedPersonName.text=booking.patientFullName
            bookedPersonAge.text=booking.patientAge.toString()
            bookedPersonSex.text=booking.patientGender
            ProblemDescriptionTextView.text=booking.problemDescription
            bookedPersonType.text=booking.personType

        }
    }

    private fun splitDayAndMonthFromDate(): Triple<String, String, String> {
        val dateString = booking.bookingDate
        val dateParts = dateString.split("-")
        val year = dateParts[0].toInt()
        val month= dateParts[1].toInt()
        val day =dateParts[2].toInt()

          val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)

        val months = resources.getStringArray(R.array.months)
        val monthName = months[month - 1]

        val dayOfWeekInt = calendar.get(Calendar.DAY_OF_WEEK)
        val dayOfWeek = Enums.DayOfWeek.entries.find { it.calendarNumber == dayOfWeekInt }?.name ?: "UNKNOWN"

        val monthDay = "$monthName $day"
        val dayName = dayOfWeek
        val yearString = year.toString()

        return Triple(monthDay,dayName,yearString)

    }
    fun buttonClickListeners(){
        with(binding){
            bookBtn.setOnClickListener {
                viewModel.confrimBooking(booking,this@ScheduleDetailsActivity)
            }
            cancelBtn.setOnClickListener {
                finish()
            }
        }

    }
}