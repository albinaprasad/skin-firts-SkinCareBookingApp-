package com.medicalhealth.healthapplication.view.ratingScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentRatingBinding
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.adapter.RatingsAdapter
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorInfoFragment
import com.medicalhealth.healthapplication.view.scheduleScreen.ScheduleActivity
import com.medicalhealth.healthapplication.viewModel.DoctorsListViewModel
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import com.medicalhealth.healthapplication.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue



@AndroidEntryPoint
class RatingFragment : Fragment() {
    lateinit var ratingBinding: FragmentRatingBinding
    private val viewModel: DoctorsListViewModel by activityViewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()

    private val mainViewModel: MainViewModel by activityViewModels()
    lateinit var adapter: RatingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        ratingBinding = FragmentRatingBinding.inflate(inflater, container, false)
        setUpAdapter()
        return (ratingBinding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadDoctors("ALL")
    }
    private fun replaceFragment(doctorInfoFragment: DoctorInfoFragment) {

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_doctor, doctorInfoFragment)
            .addToBackStack(null)
            .commit()

    }
    private fun setUpAdapter() {
         adapter = RatingsAdapter(
            { doctor ->
                sharedViewModel.selectDoctor(doctor)
                replaceFragment(DoctorInfoFragment())
            }, { doctorObj ->
                val intent = Intent(requireContext(), ScheduleActivity::class.java)
                intent.putExtra("clicked_doctor", doctorObj)
                startActivity(intent)
            }, { doctor ->
                mainViewModel.toggleFavoriteStatus(doctor.id)
                doctor.isFavorite = !doctor.isFavorite
                adapter.notifyDataSetChanged()
            }
        )
        with(ratingBinding) {
            ratingsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            ratingsRecyclerView.adapter = adapter
        }
        lifecycleScope.launch {
            viewModel.doctors.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        adapter.updateData(resource.data?.sortedWith(compareByDescending{it.rating}) ?: emptyList())
                    }

                    else -> {
                        Toast.makeText(
                            requireActivity(),
                            "error in loading data ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }
}