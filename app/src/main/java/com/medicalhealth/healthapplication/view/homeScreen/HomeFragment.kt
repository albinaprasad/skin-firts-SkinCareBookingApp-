package com.medicalhealth.healthapplication.view.homeScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.medicalhealth.healthapplication.utils.utils.addThirtyMinutes
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
    private var isFavoriteList: List<String> = emptyList()

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

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchBookingsForCurrentMonth()
        }

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
            doctorAdapter = DoctorAdapter(initialDoctors){ doctor, position ->

                viewModel.toggleFavoriteStatus(doctor.id)
                val currentList = doctorAdapter?.doctors?.toMutableList() ?: return@DoctorAdapter

                val actualIndex = currentList.indexOfFirst { it.id == doctor.id }

                if(actualIndex != -1){
                    val newFavStatus = !doctor.isFavorite
                    val copiedDoctor = doctor.copy()
                    copiedDoctor.isFavorite = newFavStatus
                    currentList[actualIndex] = copiedDoctor
                    doctorAdapter?.updateDate(currentList)
                }
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
            viewModel.currentUserDetails.collect{result ->
                when(result){
                    is Resource.Error -> {
                        val errorMessage = result.message ?: "An Unknown error occurred"
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        val user = result.data
                        binding.userNameTextView.text = user?.userName ?: "User Data Missing"
                        isFavoriteList = user?.favouriteDoctors ?: emptyList()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.appointments.collect{ result ->
                when(result){
                    is Resource.Error -> {
                        val errorMessage = result.message ?: "An Unknown error occurred"
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        val appointments = result.data ?: emptyList()
                        val doctors = viewModel.getDoctors()
                        appointments.forEach { appointment ->
                            appointment.doctorName = doctors.find { it.id == appointment.doctorId }?.name
                            appointment.endTime = addThirtyMinutes(appointment.bookingTime)
                            appointment.headerDateText = viewModel.formatRelativeDate(context, appointment.bookingDate)
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
                        val errorMessage = result.message ?: "An Unknown error occurred"
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.doctorRecyclerView.gone()
                    }
                    is Resource.Success -> {
                        binding.doctorRecyclerView.show()
                        val doctorsList = result.data ?: emptyList()
                        doctorsList.forEach { doctor ->
                            doctor.isFavorite = doctor.id in isFavoriteList
                        }
                        doctorAdapter?.updateDate(doctorsList)
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