package com.medicalhealth.healthapplication.view.favoriteScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.FragmentFavouriteDoctorsBinding
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.adapter.FavDoctorAdapter
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue


@AndroidEntryPoint
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
        lifecycleScope.launch {
            viewModel.doctors.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { doctorsList ->
                            adapter.updateData(doctorsList)
                        }
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