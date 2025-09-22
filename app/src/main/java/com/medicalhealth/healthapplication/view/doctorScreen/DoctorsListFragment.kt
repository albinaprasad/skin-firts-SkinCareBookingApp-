package com.medicalhealth.healthapplication.view.doctorScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentDoctorsListBinding
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.utils.ViewExtension.show
import com.medicalhealth.healthapplication.view.adapter.DoctorListViewAdapter
import com.medicalhealth.healthapplication.viewModel.DoctorsListViewModel
import com.medicalhealth.healthapplication.viewModel.SharedViewModel
import kotlinx.coroutines.launch


class DoctorsListFragment : Fragment() {
    private lateinit var binding: FragmentDoctorsListBinding

    private val viewModel: DoctorsListViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DoctorListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDoctorsListBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = binding.doctorsRecyclerView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DoctorListViewAdapter(requireContext(), emptyList()) { doctor ->
            sharedViewModel.selectDoctor(doctor)
            replaceFragment(DoctorInfoFragment())
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        observeViewModel()
    }

    private fun replaceFragment(doctorInfoFragment: DoctorInfoFragment) {

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_doctor, doctorInfoFragment)
            .addToBackStack(null)
            .commit()

    }

    private fun observeViewModel(){
        viewLifecycleOwner.lifecycleScope.launch {
                viewModel.doctors.collect{ result ->
                    when(result){
                        is Resource.Error -> {
                        }
                        is Resource.Loading -> {
                            recyclerView.show()
                        }
                        is Resource.Success -> {
                            recyclerView.show()
                            result.data?.let{
                                adapter.updateData(it)
                            }
                        }
                    }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setTitle(getString(R.string.doctors))
    }

}