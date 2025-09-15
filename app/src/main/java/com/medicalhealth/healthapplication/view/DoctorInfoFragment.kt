package com.medicalhealth.healthapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.viewModel.SharedViewModel


class DoctorInfoFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_info, container, false)
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTitle(getString(R.string.doctor_info))
    }


}
