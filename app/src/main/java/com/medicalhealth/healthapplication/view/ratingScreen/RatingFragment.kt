package com.medicalhealth.healthapplication.view.ratingScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.FragmentRatingBinding
import com.medicalhealth.healthapplication.view.adapter.RatingsAdapter
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import kotlin.getValue


class RatingFragment : Fragment() {
    lateinit var ratingBinding: FragmentRatingBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        ratingBinding = FragmentRatingBinding.inflate(inflater, container, false)
        setUpAdapter()
        return (ratingBinding.root)
    }

    private fun setUpAdapter() {
        val adapter = RatingsAdapter()
        with(ratingBinding) {
            ratingsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            ratingsRecyclerView.adapter = adapter
        }
        viewModel.doctors.observe(requireActivity()) { doctors ->
            if (doctors != null) {
                adapter.updateData(doctors)
            }
        }
    }
}