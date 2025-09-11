package com.medicalhealth.healthapplication.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentProfileBinding
import com.medicalhealth.healthapplication.view.adapter.MyProfileAdapter
import com.medicalhealth.healthapplication.viewModel.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView(){
        with(binding){
            val adapter = MyProfileAdapter(emptyList()){ optionSelected ->
                Toast.makeText(context, optionSelected.optionName, Toast.LENGTH_SHORT).show()
            }
            profileOptionRecyclerView.adapter = adapter
            viewModel.profileOptions.observe(viewLifecycleOwner){ optionList->
                adapter.updateData(optionList)
            }
        }
    }
}