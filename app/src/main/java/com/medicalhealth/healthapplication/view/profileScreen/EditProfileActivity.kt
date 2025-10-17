package com.medicalhealth.healthapplication.view.profileScreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
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
    private var isUploadingImage = false


    private val IMAGE_PICKER_REQUEST_CODE = 100
    private val PERMISSION_REQUEST_CODE = 101


    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            showImageInView(uri)
            mainViewmodel.uploadProfileImage(uri, this)
        }
    }

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        Log.d("EditProfileActivity", "Permission result: isGranted=$isGranted")
        if (isGranted) {
            Log.d("EditProfileActivity", "Launching image picker after permission granted")
            imagePickerLauncher.launch("image/*")
        } else {
            Log.d("EditProfileActivity", "Permission denied")
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES) &&
                !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                !shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)) {
                showToastMessage("Permission denied permanently. Please enable it in Settings.")

                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            } else {
                showToastMessage("We need permission to access your photos")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonCLickListeners()
        showUserDetails()
        observeImageUpload()


    }

    fun pickAndUploadImage() {
        val permission = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                Manifest.permission.READ_MEDIA_IMAGES
            }
            else -> {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
        }

        Log.d("EditProfileActivity", "Checking permission: $permission")

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            Log.d("EditProfileActivity", "Permission granted, launching image picker")
            imagePickerLauncher.launch("image/*")
        } else {
            Log.d("EditProfileActivity", "Requesting permission")
            permissionLauncher.launch(permission)
        }
    }

    private fun showImageInView(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri).centerCrop()
            .into(binding.profilePictureImageView)
    }

    fun observeImageUpload() {
        lifecycleScope.launch {
            mainViewmodel.profileImageUpload.collect { resource ->
                Log.d("EditProfileActivity", "Image upload state: $resource")
                when (resource) {
                    is Resource.Error -> {
                        isUploadingImage = false
                        binding.updateBtn.isEnabled = true
                        showToastMessage("Image upload failed: ${resource.message}")
                    }
                    is Resource.Loading -> {
                        isUploadingImage = true
                        showToastMessage("Processing image...")
                    }
                    is Resource.Success -> {
                        isUploadingImage = false
                        binding.updateBtn.isEnabled = true

                        mainViewmodel.refreshCurrentUserDetails()
                    }
                }
            }
        }
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
            imageEditBtn.setOnClickListener {
                if (isUploadingImage) {
                    Toast.makeText(
                        this@EditProfileActivity,
                        getString(R.string.uploading_image), Toast.LENGTH_SHORT
                    ).show()
                }
                else{
                    pickAndUploadImage()
                }

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
                        finish()
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

            if (user.profileImageUrl.isNotEmpty()) {
                    try {
                        val imageBytes = Base64.decode(user.profileImageUrl, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        if (bitmap != null) {
                            Glide.with(this@EditProfileActivity)
                                .load(bitmap)
                                .circleCrop()
                                .into(profilePictureImageView)
                            return@with
                        }
                    } catch (e: Exception) {

                    }
            }

            profilePictureImageView.setImageResource(R.drawable.profile_picture_big)  // Add this drawable if missing
        }
        currentUser = user
        }
    }
