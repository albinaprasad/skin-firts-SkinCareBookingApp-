package com.medicalhealth.healthapplication.view.scheduleScreen

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityScheduleBinding
import com.medicalhealth.healthapplication.model.data.TimeSlot
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.adapter.DateAdapterForScheduling
import com.medicalhealth.healthapplication.view.adapter.TimeSlotAdapterForScheduling
import com.medicalhealth.healthapplication.viewModel.ScheduleCalenderViewModel
import kotlin.getValue

class ScheduleActivity : BaseActivity() {
    lateinit var binding: ActivityScheduleBinding
    private val viewModel: ScheduleCalenderViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerSetUp()
        dateRecyclerViewSetUp()

        timeslotAdapterSetup()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeslotAdapterSetup() {
        val timeSlotAdapter = TimeSlotAdapterForScheduling(viewModel)
        binding.TimeRV.layoutManager = GridLayoutManager(this, 5)
        binding.TimeRV.adapter = timeSlotAdapter
        viewModel.timeSlot.observe(this) { newTimeSlots ->
            timeSlotAdapter.updateTimeSlots(newTimeSlots)
        }
        viewModel.generateTimeSlots()
    }

    private fun spinnerSetUp() {

        val spinner = binding.monthSpinner
        val months = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        val adapter = ArrayAdapter(this, R.layout.spinnner_month_item, months)
        adapter.setDropDownViewResource(R.layout.spinner_month_dropdown)
        spinner.adapter = adapter
        val currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)
        spinner.setSelection(currentMonth)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val selectedMonth = months[position]
                Toast.makeText(
                    this@ScheduleActivity,
                    "Selected: $selectedMonth",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.generateMonthDates(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun dateRecyclerViewSetUp() {
       val dateAdapter = DateAdapterForScheduling(mutableListOf())

        with(binding) {
            scheduleRecyclerView.layoutManager = LinearLayoutManager(
                this@ScheduleActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            scheduleRecyclerView.adapter = dateAdapter
            viewModel.dateList.observe(this@ScheduleActivity) { newDateList ->
                dateAdapter.updateDates(newDateList)
            }
        }
    }

}

