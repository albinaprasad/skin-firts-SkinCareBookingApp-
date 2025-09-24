package com.medicalhealth.healthapplication.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.FragmentAllAppointmentBinding
import com.medicalhealth.healthapplication.model.data.AppointmentItem
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.model.data.Schedule
import com.medicalhealth.healthapplication.model.data.UpcomingAppointment
import com.medicalhealth.healthapplication.view.adapter.AppointmentAdapter
import com.medicalhealth.healthapplication.viewModel.SharedViewModel


class AllAppointmentFragment : Fragment() {


    private lateinit var binding: FragmentAllAppointmentBinding
    private lateinit var adapter: AppointmentAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val allDoctors: List<Doctor> by lazy {
        listOf(
            Doctor(
                "1",
                "Dr. Olivia Turner, M.D.",
                "alexander_bennett",
                "Dermato-Endocrinology",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                17,
                0,
                5.0,
                40
            ),
            Doctor(
                "1",
                "Dr. Olivia Turner, M.D.",
                "alexander_bennett",
                "Dermato-Endocrinology",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                17,
                0,
                5.0,
                40
            ),
            Doctor(
                "1",
                "Dr. Olivia Turner, M.D.",
                "alexander_bennett",
                "Dermato-Endocrinology",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                17,
                0,
                5.0,
                40
            ),
            Doctor(
                "1",
                "Dr. Olivia Turner, M.D.",
                "alexander_bennett",
                "Dermato-Endocrinology",
                0,
                15,
                "",
                "",
                "",
                "",
                9,
                17,
                17,
                0,
                5.0,
                40
            ),
        )
    }
    private val completeAppointments: List<AppointmentItem> by lazy {
        allDoctors.map { AppointmentItem.Complete(it) }
    }
    private val upcomingAppointment: List<AppointmentItem> by lazy {
        listOf(
            AppointmentItem.Upcoming(
                UpcomingAppointment(
                    allDoctors[0],
                    Schedule("Sunday,12 June", "9.30AM-10.00AM")
                )
            ),
            AppointmentItem.Upcoming(
                UpcomingAppointment(
                    allDoctors[1],
                    Schedule("Friday,20 June", "2.30PM-3.00PM")
                )
            ),
            AppointmentItem.Upcoming(
                UpcomingAppointment(
                    allDoctors[2],
                    Schedule("Tuesday,15 June", "9.30AM-10.00AM")
                )
            ),
            AppointmentItem.Upcoming(
                UpcomingAppointment(
                    allDoctors[3],
                    Schedule("Monday,14 June", "3.00PM-3.30PM")
                )
            ),
        )
    }
    private val cancelledAppointments: List<AppointmentItem> by lazy {
        allDoctors.map { AppointmentItem.Cancelled(it) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllAppointmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        adapter = AppointmentAdapter(requireContext(), emptyList())
        binding.completeAppointmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.completeAppointmentRecyclerView.adapter = adapter

        sharedViewModel.currentDataList.observe(viewLifecycleOwner) { newData ->
            adapter.updateChanges(newData as List<AppointmentItem>)
        }

        sharedViewModel.setData(completeAppointments)
        binding.btnComplete.isActivated = true
        setUpOnListener()


    }

    private fun setUpOnListener() {

        binding.btnComplete.setOnClickListener {
            updateButtonState(binding.btnComplete)
            sharedViewModel.setData(completeAppointments)
        }
        binding.btnUpcoming.setOnClickListener {
            updateButtonState(binding.btnUpcoming)
            sharedViewModel.setData(upcomingAppointment)
        }
        binding.btnCancelled.setOnClickListener {
            updateButtonState(binding.btnCancelled)
            sharedViewModel.setData(cancelledAppointments)
        }
    }

    private fun updateButtonState(activeButton: View) {
        binding.btnComplete.isActivated = (activeButton == binding.btnComplete)
        binding.btnUpcoming.isActivated = (activeButton == binding.btnUpcoming)
        binding.btnCancelled.isActivated = (activeButton == binding.btnCancelled)
    }


}