package com.medicalhealth.healthapplication.view.homeScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private val viewModel: MainViewModel by activityViewModels()
    private var doctorAdapter: DoctorAdapter? = null
    private var scheduleAdapter: ScheduleAdapter? = null

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
            scheduleAdapter= ScheduleAdapter(emptyList())
            scheduleRecyclerView.adapter = scheduleAdapter



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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.appointments.collect{ result ->
                when(result){
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val appointments = result.data ?: emptyList()
                        val doctors = viewModel.getDoctors()
                        appointments.forEach { appointment ->
                            appointment.doctorName = doctors.find { it.id == appointment.doctorId }?.name
                            appointment.endTime = viewModel.addThirtyMinutes(appointment.bookingTime)
                            appointment.headerDateText = viewModel.formatRelativeDate(appointment.bookingDate)
                        }
                        (binding.scheduleRecyclerView.adapter as? ScheduleAdapter)?.updateData(appointments)
                        setUpRecyclerViewListener()
                    }
                }

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

    private fun setUpRecyclerViewListener(){
        val layoutManager = binding.scheduleRecyclerView.layoutManager as LinearLayoutManager
        binding.scheduleRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if(firstVisibleItemPosition != RecyclerView.NO_POSITION){
                    updateHeaderDate(firstVisibleItemPosition)
                }
            }
        })
    }

    private fun updateHeaderDate(position: Int){
        val item = scheduleAdapter?.getItemAt(position)
        binding.headerDate.text = item?.headerDateText
    }
}