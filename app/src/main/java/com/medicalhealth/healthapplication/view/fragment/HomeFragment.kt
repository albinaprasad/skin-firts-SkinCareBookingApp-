package com.medicalhealth.healthapplication.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentHomeBinding
import com.medicalhealth.healthapplication.view.adapter.DateAdapter
import com.medicalhealth.healthapplication.view.adapter.DoctorAdapter
import com.medicalhealth.healthapplication.view.adapter.ScheduleAdapter
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorsActivity
import com.medicalhealth.healthapplication.view.notificationScreen.NotificationActivity
import com.medicalhealth.healthapplication.viewModel.MainViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        setUpListeners()
        return binding.root
    }

    private fun setUpRecyclerView() {
        viewModel.dates.value?.let { dates ->
            val dateAdapter = DateAdapter(dates) { selectedDate ->
                viewModel.selectDate(selectedDate)
            }
            with(binding) {
                this.dateRecyclerView.layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                this.dateRecyclerView.adapter = dateAdapter

                viewModel.dates.observe(viewLifecycleOwner) { updatedDates ->
                    dateAdapter.notifyDataSetChanged()
                }
            }
        }

        with(binding) {
            this.scheduleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            viewModel.appointment.observe(viewLifecycleOwner) { appointmentsList ->
                val appointmentAdapter = ScheduleAdapter(appointmentsList)
                this.scheduleRecyclerView.adapter = appointmentAdapter
            }

            this.doctorRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            viewModel.doctors.observe(viewLifecycleOwner) { doctorsList ->
                val adapter = doctorsList?.let {
                    DoctorAdapter(it) { doctor ->
                        viewModel.toggleFavoriteStatus(doctor.id)
                    }
                }
                this.doctorRecyclerView.adapter = adapter
            }
        }

    }

    private fun setUpListeners(){
        with(binding){
            this.allDoctorsBtn.setOnClickListener {
                val intent = Intent(requireContext(), DoctorsActivity::class.java)
                startActivity(intent)
            }
            this.notificationBtn.setOnClickListener {
                val intent = Intent(requireContext(), NotificationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}