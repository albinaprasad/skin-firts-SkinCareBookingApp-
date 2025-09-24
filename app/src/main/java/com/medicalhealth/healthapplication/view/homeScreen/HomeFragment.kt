package com.medicalhealth.healthapplication.view.homeScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentHomeBinding
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.utils.ViewExtension.gone
import com.medicalhealth.healthapplication.utils.ViewExtension.show
import com.medicalhealth.healthapplication.view.adapter.DateAdapter
import com.medicalhealth.healthapplication.view.adapter.DoctorAdapter
import com.medicalhealth.healthapplication.view.adapter.ScheduleAdapter
import com.medicalhealth.healthapplication.view.notificationScreen.NotificationActivity
import com.medicalhealth.healthapplication.view.notificationSetting.NotificationSettingsActivity
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private var doctorAdapter: DoctorAdapter? = null

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
        val today = java.util.Calendar.getInstance()
        Log.d("message", "Month: ${today.get(java.util.Calendar.MONTH)}")
        Log.d("message", "Days: ${today.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)}")
        val currentDay = today.get(java.util.Calendar.DAY_OF_MONTH)
        Log.d("message", "$today")
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
            val initialDoctors = emptyList<Doctor>()
            doctorAdapter = DoctorAdapter(initialDoctors){ doctor ->
                viewModel.toggleFavoriteStatus("")
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.doctors.collect{ result ->
                when(result){
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {
                        binding.doctorRecyclerView.gone()
                    }
                    is Resource.Success -> {
                        binding.doctorRecyclerView.show()
                        result.data?.let { doctorAdapter?.updateDate(it) }
                    }
                }

            }
        }
    }

    private fun setUpListeners(){
        with(binding){
            allDoctorsBtn.setOnClickListener {
                (activity as? MainActivity)?.startDoctorActivity()
            }
            notificationBtn.setOnClickListener {
                val intent = Intent(requireActivity(), NotificationActivity::class.java)
                startActivity(intent)
            }
            settingsBtn.setOnClickListener {
                val intent = Intent(requireActivity(), NotificationSettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}