package com.medicalhealth.healthapplication.view.profileScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityEditProfileBinding
import com.medicalhealth.healthapplication.model.data.Users
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.settingScreen.SettingsActivity
import com.medicalhealth.healthapplication.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    lateinit var currentUser: Users
    val mainViewmodel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonCLickListeners()
        showUserDetails()

    }
    fun buttonCLickListeners(){
        with(binding){
            backBtn.setOnClickListener {
                onBackPressed()
            }
            updateBtn.setOnClickListener {
                updateData()
            }
            settingsImage.setOnClickListener {
                val intent = Intent(this@EditProfileActivity, SettingsActivity::class.java)
                startActivity(intent)

            }
        }
    }

    fun updateData(){
        with(binding){
            var newName = fullNameEditText.text.toString().trim()
            var newPhone = phoneNumberEditText.text.toString().trim()
            var newEmail = emailEditText.text.toString().trim()
            var newDob = dobEditText.text.toString().trim()

            when {
                 newName.isEmpty()->{
                     showToastMessage("Name field cannot be empty")
                     return
                 }
                newPhone.isEmpty() ->{
                        showToastMessage(getString(R.string.name_cannot_be_empty))
                    return
                }
                newEmail.isEmpty()->{
                    showToastMessage(getString(R.string.phone_cannot_be_empty))
                    return
                }
                newDob.isEmpty()->{
                    showToastMessage(getString(R.string.Dob_cannot_be_empty))
                    return
                }
            }

            val updatedUser = currentUser.copy(
                userName = newName ,
                mobileNumber = newPhone.toLongOrNull() ?: currentUser.mobileNumber,
                userEmail = newEmail ,
                dateOfBirth = newDob
            )

                mainViewmodel.updateUserDetails(updatedUser)
            }
        lifecycleScope.launch {
            mainViewmodel.updatedUser.collect { resource ->

                when(resource){
                    is Resource.Error-> {
                        showToastMessage(getString(R.string.profile_updated_unsucessfully))
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        showToastMessage(getString(R.string.profile_updated_sucessfully))
                    }
                }
            }
        }



    }
    fun showUserDetails(){
        lifecycleScope.launch {
            mainViewmodel.fetchCurrentUserDetails()
            mainViewmodel.currentUserDetails.collect { resource ->
                when(resource){
                    is Resource.Error -> {

                    }
                    is Resource.Loading ->{

                    }
                    is Resource.Success -> {
                        resource.data?.let{ it->
                           displayUserData(it)
                            currentUser=it
                        }
                    }
                }
            }
        }

    }

    fun displayUserData(user: Users){
        with(binding){
            fullNameEditText.setText(user.userName)
            phoneNumberEditText.setText(user.mobileNumber.toString())
            emailEditText.setText(user.userEmail)
            dobEditText.setText(user.dateOfBirth)
        }
    }
}