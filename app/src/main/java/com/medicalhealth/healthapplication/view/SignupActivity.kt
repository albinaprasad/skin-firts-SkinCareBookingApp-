package com.medicalhealth.healthapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.medicalhealth.healthapplication.databinding.ActivitySignupBinding
import com.medicalhealth.healthapplication.view.ui.loginScreen.LoginActivity
import com.medicalhealth.healthapplication.viewModel.AuthenticationViewModel

class SignupActivity : BaseActivity() {
    private lateinit var binding:ActivitySignupBinding
    val signupo:AuthenticationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpOnListener()
        setUpOnObserver()

        }

    private fun setUpOnObserver() {
        signupo.isResult.observe(this){
            it.onSuccess {
                Toast.makeText(applicationContext, "Signup Successful", Toast.LENGTH_SHORT).show()
                val intent =Intent(this@SignupActivity,MainActivity::class.java)
                startActivity(intent)
                finish()

            }
            it.onFailure { exception->
                Toast.makeText(this, "Sign Up Failed: {$exception?.email}", Toast.LENGTH_SHORT).show()
                Log.d("log_message","{$exception?.email}")

            }

        }
    }

    private fun setUpOnListener() {
        with(binding) {
            btnBackSignUp.setOnClickListener {
                val intent = Intent(this@SignupActivity, WelcomeScreenActivity::class.java)
                startActivity(intent)
            }
            tvlogin.setOnClickListener {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            btnSignUp.setOnClickListener {
                val email = binding.tvEmail.text.toString()
                val password = binding.tvPasswordSignUp.text.toString()
                val userName = binding.tvUserName.text.toString()
                val mobileNumberStr = binding.tvMobileNumber.text.toString()
                val dob = binding.tvDateOfBirth.text.toString()

                if (password.isEmpty() || email.isEmpty() || userName.isEmpty() || dob.isEmpty() || mobileNumberStr.isEmpty()) {
                    Toast.makeText(this@SignupActivity, "PlEASE ENTER THE EMAIL AND PASSWORD", Toast.LENGTH_SHORT)
                        .show()
                } else if (mobileNumberStr.length < 10 || mobileNumberStr.length > 10) {
                    Toast.makeText(this@SignupActivity, "PLEASE ENTER A VALID PHONE NUMBER", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    try {
                        val mobileNumber = mobileNumberStr.toLong()
                        signupo.signUp(email, password, userName, mobileNumber, dob)
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            this@SignupActivity,
                            "Please enter a valid number for mobile number",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


    }
}
