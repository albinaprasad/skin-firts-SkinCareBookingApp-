package com.medicalhealth.healthapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.view.DoctorsList
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentDoctorsListBinding
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorListViewAdapter
import com.medicalhealth.healthapplication.view.doctorScreen.Doctors
import com.medicalhealth.healthapplication.viewModel.DoctorsListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DoctorsList.newInstance] factory method to
 * create an instance of this fragment.
 */
class DoctorsList : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var _binding: FragmentDoctorsListBinding
    private val viewModel: DoctorsListViewModel by viewModels()
    private lateinit var adapter: DoctorListViewAdapter // Your custom adapter
    private lateinit var dataList: List<Doctors>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=  FragmentDoctorsListBinding.inflate(inflater, container, false)
        val view = _binding.root

        adapter = DoctorListViewAdapter(requireContext(),emptyList())

        _binding.doctorsRecyclerView.layoutManager = LinearLayoutManager(context)
        _binding.doctorsRecyclerView.adapter = adapter

        viewModel.doctors.observe(viewLifecycleOwner) { doctors ->
            (_binding.doctorsRecyclerView.adapter as DoctorListViewAdapter ).updateData(doctors)
        }
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Release the binding object to prevent memory leaks

    }
}