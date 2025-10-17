package com.medicalhealth.healthapplication.view.profileScreen


import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FragmentProfileBinding
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.helpCentre.HelpCenterActivity
import com.medicalhealth.healthapplication.view.PrivacyPolicyActivity
import com.medicalhealth.healthapplication.view.adapter.MyProfileAdapter
import com.medicalhealth.healthapplication.view.settingScreen.SettingsActivity
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import com.medicalhealth.healthapplication.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val mainViewmodel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        setupUserDetails()

        Log.d("ProfileFragment", "Host Activity: ${requireActivity()::class.simpleName}")
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        mainViewmodel.refreshCurrentUserDetails()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeViewModel()

    }





    fun setupUserDetails(){

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewmodel.currentUserDetails.collectLatest { resource ->

                when(resource){
                    is Resource.Error<*> -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success ->
                    {
                        with(binding){

                           profileNameTextView.text = resource.data?.userName
                            if (resource.data?.profileImageUrl!!.isNotEmpty()) {
                                try {
                                    val imageBytes = Base64.decode(resource.data.profileImageUrl, Base64.DEFAULT)
                                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                                    if (bitmap != null) {
                                        Glide.with(requireActivity())
                                            .load(bitmap)
                                            .circleCrop()
                                            .into(profilePictureImageView)
                                        return@with
                                    }
                                } catch (e: Exception) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        with(binding){
            val adapter = MyProfileAdapter(emptyList()){ optionSelected ->
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
        else if (selectedOption == "Privacy Policy"){

            val intent = Intent(requireActivity(), PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }
    }
}