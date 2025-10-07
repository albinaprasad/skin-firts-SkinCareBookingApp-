package com.medicalhealth.healthapplication.view.doctorScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentDoctorsListBinding
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.utils.ViewExtension.show
import com.medicalhealth.healthapplication.view.adapter.DoctorListViewAdapter
import com.medicalhealth.healthapplication.view.scheduleScreen.ScheduleActivity
import com.medicalhealth.healthapplication.viewModel.DoctorsListViewModel
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import com.medicalhealth.healthapplication.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DoctorsListFragment : Fragment() {
    private lateinit var binding: FragmentDoctorsListBinding

    private var filterType: String = "ALL"

    private val viewModel: DoctorsListViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
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
        arguments?.let {
            filterType = it.getString(ARG_FILTER_TYPE, "ALL")
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.refreshCurrentUserDetails()

        adapter = DoctorListViewAdapter(requireContext(), emptyList(), { doctor ->
            sharedViewModel.selectDoctor(doctor)
            replaceFragment(DoctorInfoFragment())
        },
            { doctorObj ->
            val intent = Intent(requireContext(), ScheduleActivity::class.java)
            intent.putExtra("clicked_doctor", doctorObj)
            startActivity(intent)
        },
            {
                doctor->mainViewModel.toggleFavoriteStatus(doctor.id)
                doctor.isFavorite = !doctor.isFavorite
                adapter.notifyDataSetChanged()
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        viewModel.loadDoctors(filterType)
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
                viewModel.doctors.collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                        }

                        is Resource.Loading -> {
                            recyclerView.show()
                        }

                        is Resource.Success -> {
                            recyclerView.show()
                            result.data?.let { doctors->

                                val userDetails = mainViewModel.currentUserDetails.value
                                val favoriteDoctorIds= if (userDetails is Resource.Success && userDetails.data != null) {
                                    userDetails.data.favouriteDoctors
                                } else {
                                    emptyList()
                                }
                                doctors.forEach { doctor ->  doctor.isFavorite =  favoriteDoctorIds.contains(doctor.id) }
                                adapter.updateData(doctors)

                            }
                        }
                    }
                }

            }

    }

    companion object{
        const val ARG_FILTER_TYPE = "filter_type"

        fun newInstance(filterType: String): DoctorsListFragment{
            val fragment = DoctorsListFragment()
            val args = Bundle()
            args.putString(ARG_FILTER_TYPE, filterType)
            fragment.arguments = args
            return fragment
        }
    }
}