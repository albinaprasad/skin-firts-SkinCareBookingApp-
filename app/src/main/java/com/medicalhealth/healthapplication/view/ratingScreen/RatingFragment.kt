package com.medicalhealth.healthapplication.view.ratingScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.FragmentRatingBinding
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.adapter.RatingsAdapter
import com.medicalhealth.healthapplication.viewModel.DoctorsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue



class RatingFragment : Fragment() {
    lateinit var ratingBinding: FragmentRatingBinding
    private val viewModel: DoctorsListViewModel by activityViewModels()

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

    private fun setUpAdapter() {
        val adapter = RatingsAdapter()
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