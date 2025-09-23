package com.medicalhealth.healthapplication.view.doctorScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentDoctorInfoBinding
import com.medicalhealth.healthapplication.view.scheduleScreen.ScheduleActivity
import com.medicalhealth.healthapplication.viewModel.SharedViewModel


class DoctorInfoFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var binding: FragmentDoctorInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDoctorInfoBinding.inflate(inflater, container, false)
        buttonClickListeners()
        return binding.root
    }

     fun buttonClickListeners() {

         with(binding)
         {
             btnSchedule.setOnClickListener {
                 val intent = Intent(requireContext(), ScheduleActivity::class.java)
                 startActivity(intent)
             }
         }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTitle(getString(R.string.doctor_info))
    }
}
