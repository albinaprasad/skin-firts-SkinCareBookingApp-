package com.medicalhealth.healthapplication.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.medicalhealth.healthapplication.databinding.FragmentAllAppointmentBinding
import com.medicalhealth.healthapplication.model.data.Appointment
import com.medicalhealth.healthapplication.model.data.AppointmentItem
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.CancelAppointment
import com.medicalhealth.healthapplication.view.adapter.AppointmentAdapter
import com.medicalhealth.healthapplication.viewModel.SharedViewModel

class AllAppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAllAppointmentBinding
    private lateinit var adapter: AppointmentAdapter
    private val currentUser = FirebaseAuth.getInstance().currentUser?.uid
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var cancelActivityResultLauncher: ActivityResultLauncher<Intent>


    private var currentStatus: String = "COMPLETED"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cancelActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {result ->
            if(result.resultCode == Activity.RESULT_OK){
                Log.d("Fragment", "Cancellation confirmed on child screen. Forcing refresh.")
                sharedViewModel.filterAppointmentsByStatus(currentStatus)
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val cancelClickListener: (String) -> Unit = { documentId ->
            Log.d("CancelFlow", "Attempting to launch CancelAppointment for ID: $documentId") // Log 1
            val intent = Intent(requireContext(), CancelAppointment::class.java).apply {
                putExtra("BOOKING_ID", documentId)
            }
            cancelActivityResultLauncher.launch(intent)
            Log.d("CancelFlow", "Launch call completed.")


        }
        val completeClickListener: (String) -> Unit = { documentId ->
            Log.d("CancelFlow", "Attempting to launch CancelAppointment for ID: $documentId")

            sharedViewModel.changeTheStatus(documentId, "COMPLETED")
        }


        adapter = AppointmentAdapter(requireContext(), emptyList(), onCancelClick = cancelClickListener, onCompleteClick = completeClickListener)
        binding.completeAppointmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.completeAppointmentRecyclerView.adapter = adapter


        binding.btnComplete.isActivated = true

        observeAppointments()


        setUpOnListener()
    }

    private fun observeAppointments() {
        if (currentUser != null) {
            sharedViewModel.filteredAppointments.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Loading -> {

                        Log.d("Fragment", "Loading appointments...")
                    }

                    is Resource.Error -> {

                        Log.e("Fragment", "Error fetching appointments: ${resource.message}")
                        adapter.updateChanges(emptyList())
                    }

                    is Resource.Success -> {

                        val appointments = resource.data ?: emptyList()
                        Log.d(
                            "Fragment",
                            "Received ${appointments.size} appointments for status: $currentStatus"
                        )


                        updateAdapter(appointments)
                    }
                }
            }
        }
    }
    private fun updateAdapter(appointments: List<Appointment>) {

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

                sharedViewModel.filterAppointmentsByStatus(status = currentStatus)
            }
        }
        binding.btnUpcoming.setOnClickListener {
            updateButtonState(binding.btnUpcoming)
            currentStatus = "UPCOMING" // Update status
            if (currentUser != null) {

                sharedViewModel.filterAppointmentsByStatus(status = currentStatus)
            }
        }
        binding.btnCancelled.setOnClickListener {
            updateButtonState(binding.btnCancelled)
            currentStatus = "CANCELLED" // Update status
            if (currentUser != null) {
                sharedViewModel.filterAppointmentsByStatus(status = currentStatus)
            }
        }
    }

    private fun updateButtonState(activeButton: View) {
        binding.btnComplete.isActivated = (activeButton == binding.btnComplete)
        binding.btnUpcoming.isActivated = (activeButton == binding.btnUpcoming)
        binding.btnCancelled.isActivated = (activeButton == binding.btnCancelled)
    }

}