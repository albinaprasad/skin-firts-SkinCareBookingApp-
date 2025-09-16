package com.medicalhealth.healthapplication.view.homeScreen

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeViewModel()
        setUpListeners()
    }

    private fun setUpRecyclerView() {
        with(binding){
            dateRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val initialDates = viewModel.dates.value.orEmpty()
            val dateAdapter = DateAdapter(initialDates) {selectedDate ->
                viewModel.selectDate(selectedDate)
            }
            dateRecyclerView.adapter = dateAdapter

            scheduleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            val initialAppointments = viewModel.appointment.value.orEmpty()
            scheduleRecyclerView.adapter = ScheduleAdapter(initialAppointments)

            doctorRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            val initialDoctors = viewModel.doctors.value.orEmpty()
            val doctorAdapter = DoctorAdapter(initialDoctors){ doctor ->
                viewModel.toggleFavoriteStatus(doctor.id)
            }
            doctorRecyclerView.adapter = doctorAdapter
        }
    }

    private fun observeViewModel(){
        viewModel.dates.observe(viewLifecycleOwner){ updatedDates ->
            if (updatedDates != null) {
                (binding.dateRecyclerView.adapter as? DateAdapter)?.updateData(updatedDates)
            }
        }
        viewModel.appointment.observe(viewLifecycleOwner){ appointmentsList ->
            if(appointmentsList != null){
                (binding.scheduleRecyclerView.adapter as? ScheduleAdapter)?.updateData(appointmentsList)
            }
        }
        viewModel.doctors.observe(viewLifecycleOwner){doctorsList ->
            if(doctorsList != null) {
                (binding.doctorRecyclerView.adapter as? DoctorAdapter)?.updateDate(doctorsList)
            }
        }
    }

    private fun setUpListeners(){
        with(binding){
            allDoctorsBtn.setOnClickListener {
                val intent = Intent(requireActivity(), DoctorsActivity::class.java)
                startActivity(intent)
            }
            notificationBtn.setOnClickListener {
                val intent = Intent(requireActivity(), NotificationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}