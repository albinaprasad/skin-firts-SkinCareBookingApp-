package com.medicalhealth.healthapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentDoctorsListBinding
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.view.adapter.DoctorListViewAdapter

import com.medicalhealth.healthapplication.viewModel.DoctorsListViewModel
import com.medicalhealth.healthapplication.viewModel.SharedViewModel


class DoctorsList : Fragment() {
    private lateinit var _binding: FragmentDoctorsListBinding

    private val viewModel: DoctorsListViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()
    private lateinit var dataList: List<Doctor>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDoctorsListBinding.inflate(inflater, container, false)
        val view = _binding.root

        val adapter = DoctorListViewAdapter(requireContext(), emptyList()) { doctor ->
            sharedViewModel.selectDoctor(doctor)
            replaceFragment(DoctorInfoFragment())
        }

        _binding.doctorsRecyclerView.layoutManager = LinearLayoutManager(context)
        _binding.doctorsRecyclerView.adapter = adapter

        viewModel.doctors.observe(viewLifecycleOwner) { doctors ->
           (adapter).updateData(doctors)
        }
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()


    }








    private fun replaceFragment(doctorInfoFragment: DoctorInfoFragment) {

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, doctorInfoFragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTitle(getString(R.string.doctors))
    }



}