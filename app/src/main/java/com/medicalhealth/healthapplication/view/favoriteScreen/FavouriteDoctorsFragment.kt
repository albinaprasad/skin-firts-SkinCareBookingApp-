package com.medicalhealth.healthapplication.view.favoriteScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.FragmentFavouriteDoctorsBinding
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.adapter.FavDoctorAdapter
import com.medicalhealth.healthapplication.view.scheduleScreen.ScheduleActivity
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
        loadFavoriteDoctors()
        return FavBinding.root
    }

     fun loadFavoriteDoctors(){
         viewLifecycleOwner.lifecycleScope.launch {
             viewModel.fetchCurrentUserDetails()
             val userId = viewModel.currentUserDetails.value.data?.uid
             if (userId != null) {
                 viewModel.loadFavoriteDoctors(userId)
             } else {
                 Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
             }
         }
    }

    private fun setUpAdapter() {
        val adapter = FavDoctorAdapter(
            { doctorObj ->
            val intent = Intent(requireContext(), ScheduleActivity::class.java)
            intent.putExtra("clicked_doctor", doctorObj)
            startActivity(intent) }
        )

        with(FavBinding) {
            favRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            favRecyclerView.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteDoctors.collect { resource ->

                    when(resource){
                        is Resource.Loading ->{
                        }
                        is Resource.Success->{
                            resource.data?.let{
                                it->
                                adapter.updateData(it)
                            }
                        }
                        is Resource.Error<*> -> {

                        }
                    }
                }
            }
        }
    }


}