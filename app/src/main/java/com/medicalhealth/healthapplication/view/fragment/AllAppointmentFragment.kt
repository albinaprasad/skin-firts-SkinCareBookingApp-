package com.medicalhealth.healthapplication.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.medicalhealth.healthapplication.databinding.FragmentAllAppointmentBinding
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.AppointmentItem
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.adapter.AppointmentAdapter
import com.medicalhealth.healthapplication.viewModel.SharedViewModel

class AllAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAllAppointmentBinding
    private lateinit var adapter: AppointmentAdapter
    // Removed unused authentication and appointments fields
    private val currentUser = FirebaseAuth.getInstance().currentUser?.uid
    private val sharedViewModel: SharedViewModel by activityViewModels()

    // ⭐ Stores the currently selected status to correctly package the data for the adapter
    private var currentStatus: String = "COMPLETED"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize adapter with an empty list
        adapter = AppointmentAdapter(requireContext(), emptyList())
        binding.completeAppointmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.completeAppointmentRecyclerView.adapter = adapter

        // Set initial state
        binding.btnComplete.isActivated = true

        // ⭐ Start observing the filtered data stream
        observeAppointments()

        // Set up button click listeners
        setUpOnListener()
    }

    private fun observeAppointments() {
        if (currentUser != null) {
            sharedViewModel.filteredAppointments.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // binding.progressBar.visibility = View.VISIBLE
                        Log.d("Fragment", "Loading appointments...")
                    }

                    is Resource.Error -> {
                        // binding.progressBar.visibility = View.GONE
                        Log.e("Fragment", "Error fetching appointments: ${resource.message}")
                        adapter.updateChanges(emptyList())
                    }

                    is Resource.Success -> {
                        // binding.progressBar.visibility = View.GONE
                        val appointments = resource.data ?: emptyList()
                        Log.d(
                            "Fragment",
                            "Received ${appointments.size} appointments for status: $currentStatus"
                        )

                        // ⭐ Update the adapter with the correct AppointmentItem wrapper
                        updateAdapter(appointments)
                    }
                }
            }
        }
    }
    private fun updateAdapter(appointments: List<Appointment>) {
        // ⭐ FIX: Map the list of Appointments into a list of AppointmentItem wrappers.
        // This allows the RecyclerView to see multiple different view types if necessary,
        // and correctly reports the item count.

        val appointmentItems: List<AppointmentItem> = appointments.map { appointment ->
            when (currentStatus) {
                "COMPLETED" -> AppointmentItem.Complete(listOf(appointment))
                "UPCOMING" -> AppointmentItem.Upcoming(listOf(appointment))
                "CANCELLED" -> AppointmentItem.Cancelled(listOf(appointment))
                else -> AppointmentItem.Complete(listOf(appointment))
            }
        }

        // Pass the list of individual AppointmentItem wrappers to the adapter
        adapter.updateChanges(appointmentItems)
    }




    private fun setUpOnListener() {

        binding.btnComplete.setOnClickListener {
            updateButtonState(binding.btnComplete)
            currentStatus = "COMPLETED" // Update status
            if (currentUser != null) {
                // ⭐ Call the ViewModel's filtering function
                sharedViewModel.filterAppointmentsByStatus(status = currentStatus)
            }
        }
        binding.btnUpcoming.setOnClickListener {
            updateButtonState(binding.btnUpcoming)
            currentStatus = "UPCOMING" // Update status
            if (currentUser != null) {
                // ⭐ Call the ViewModel's filtering function
                sharedViewModel.filterAppointmentsByStatus(status = currentStatus)
            }
        }
        binding.btnCancelled.setOnClickListener {
            updateButtonState(binding.btnCancelled)
            currentStatus = "CANCELLED" // Update status
            if (currentUser != null) {
                // ⭐ Call the ViewModel's filtering function
                sharedViewModel.filterAppointmentsByStatus(status = currentStatus)
            }
        }
    }

    private fun updateButtonState(activeButton: View) {
        binding.btnComplete.isActivated = (activeButton == binding.btnComplete)
        binding.btnUpcoming.isActivated = (activeButton == binding.btnUpcoming)
        binding.btnCancelled.isActivated = (activeButton == binding.btnCancelled)
    }

    // ⭐ The getAndReplaceDoctorDetails logic has been moved and refactored into the ViewModel
    //    to ensure data consistency before UI updates.
}