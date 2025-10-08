package com.medicalhealth.healthapplication.view.passwordManagerScreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityPasswordManagerBinding
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.viewModel.AuthenticationViewModel
import kotlinx.coroutines.launch

class PasswordManagerActivity : BaseActivity() {
    lateinit var binding: ActivityPasswordManagerBinding
    val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPasswordManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonClickListeners()
    }
    fun buttonClickListeners(){
        with(binding){
            backButton.setOnClickListener {
                onBackPressed()
            }
            changePasswordBtn.setOnClickListener {
                changeUserPassword()

            }
        }
    }
    fun changeUserPassword(){
        with(binding) {
            val currentPassword = currentPasswordEditText.text.toString().trim()
            val newPassword = newPasswordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            viewModel.changePassword(currentPassword, newPassword, confirmPassword)

            lifecycleScope.launch {
                viewModel.changePasswordState.collect { resource ->
                 when(resource){

                     is Resource.Success ->{
                         Toast.makeText(applicationContext, "Password changed successfully", Toast.LENGTH_LONG).show()
                         onBackPressed()
                     }
                     is Resource.Error -> {

                     }
                     is Resource.Loading -> {
                        binding.changePasswordBtn.alpha = 0.5f
                         binding.changePasswordBtn.text = getString(R.string.changing_password)
                     }
                     else -> {

                     }
                 }
                }
            }
        }
    }
}