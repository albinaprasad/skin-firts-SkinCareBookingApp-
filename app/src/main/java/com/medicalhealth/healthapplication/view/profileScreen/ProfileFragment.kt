package com.medicalhealth.healthapplication.view.profileScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentProfileBinding
import com.medicalhealth.healthapplication.view.HelpCenterActivity
import com.medicalhealth.healthapplication.view.adapter.MyProfileAdapter
import com.medicalhealth.healthapplication.view.settingScreen.SettingsActivity
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeViewModel()
    }

    private fun setUpRecyclerView(){
        with(binding){
            val adapter = MyProfileAdapter(emptyList()){ optionSelected ->
                Toast.makeText(context, optionSelected.optionName, Toast.LENGTH_SHORT).show()
                startActivity(optionSelected.optionName)
            }
            profileOptionRecyclerView.adapter = adapter
        }
    }

    private fun observeViewModel(){
        viewModel.itemOptions.observe(viewLifecycleOwner){ optionList->
            if(optionList != null){
                (binding.profileOptionRecyclerView.adapter as? MyProfileAdapter)?.updateData(optionList)
            }
        }
    }

    private fun startActivity(selectedOption: String){
        if(selectedOption == "Profile") {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            startActivity(intent)
        }
        else if(selectedOption == "Help"){
            val intent = Intent(requireActivity(), HelpCenterActivity::class.java)
            startActivity(intent)
        }
        else if(selectedOption == "Logout"){
            viewModel.signOut()
            activity?.finish()
        }
        else if (selectedOption == "Settings"){
            val intent = Intent(requireActivity(), SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}