package com.medicalhealth.healthapplication.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityMainBinding
import com.medicalhealth.healthapplication.view.adapter.DateAdapter
import com.medicalhealth.healthapplication.view.adapter.DoctorAdapter
import com.medicalhealth.healthapplication.view.adapter.ScheduleAdapter
import com.medicalhealth.healthapplication.viewModel.MainViewModel

class MainActivity : BaseActivity() {
    class MainActivity : AppCompatActivity() {
        private lateinit var mainBinding: ActivityMainBinding
        private val viewModel: MainViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            mainBinding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(mainBinding.root)

            setUpRecyclerView()
        }

        private fun setUpRecyclerView() {
            viewModel.dates.value?.let { dates ->
                val dateAdapter = DateAdapter(dates) { selectedDate ->
                    viewModel.selectDate(selectedDate)
                }
                with(mainBinding) {
                    dateRecyclerView.layoutManager =
                        LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    dateRecyclerView.adapter = dateAdapter

                    viewModel.dates.observe(this@MainActivity) { updatedDates ->
                        dateAdapter.notifyDataSetChanged()
                    }
                }
            }

            with(mainBinding) {
                scheduleRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                viewModel.appointment.observe(this@MainActivity) { appointmentsList ->
                    val appointmentAdapter = ScheduleAdapter(appointmentsList)
                    scheduleRecyclerView.adapter = appointmentAdapter
                }

                doctorRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                viewModel.doctors.observe(this@MainActivity) { doctorsList ->
                    val adapter = DoctorAdapter(doctorsList) { doctor ->
                        viewModel.toggleFavoriteStatus(doctor.id)
                    }
                    doctorRecyclerView.adapter = adapter
                }
        }

    }

}
    }