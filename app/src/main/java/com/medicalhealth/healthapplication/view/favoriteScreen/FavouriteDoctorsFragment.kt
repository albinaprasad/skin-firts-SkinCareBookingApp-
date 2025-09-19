package com.medicalhealth.healthapplication.view.favoriteScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.FragmentFavouriteDoctorsBinding
import com.medicalhealth.healthapplication.view.adapter.FavDoctorAdapter
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import kotlin.getValue

class FavouriteDoctorsFragment : Fragment() {
  lateinit var FavBinding: FragmentFavouriteDoctorsBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        FavBinding = FragmentFavouriteDoctorsBinding.inflate(inflater, container, false)

        setUpAdapter()
        return FavBinding.root
    }

    private fun setUpAdapter() {
        val adapter = FavDoctorAdapter()
        with(FavBinding) {
            favRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            favRecyclerView.adapter = adapter
        }
        viewModel.doctors.observe(requireActivity()) { doctors ->
            if (doctors != null) {
                adapter.updateData(doctors)
            }
        }
    }


}