package com.medicalhealth.healthapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.R
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
            result ->
            result.onSuccess { user ->
                Toast.makeText(applicationContext, "Signup Successful", Toast.LENGTH_SHORT).show()
                val intent =Intent(this@SignupActivity,MainActivity::class.java)
                startActivity(intent)
                finish()

            }
            result.onFailure { exception->
                Toast.makeText(this, "Sign Up Failed: {$exception?.email}", Toast.LENGTH_SHORT).show()
                Log.d("mathews","{$exception?.email}")

            }

        }
    }

    private fun setUpOnListener() {
        binding.btnBackSignUp.setOnClickListener {
            val intent = Intent(this@SignupActivity,WelcomeScreenActivity::class.java)
            startActivity(intent)
        }
        binding.tvlogin.setOnClickListener {
            val intent = Intent(this@SignupActivity,LoginActivity::class.java)
            startActivity(intent)
        }
       binding.btnSignUp.setOnClickListener {
           val email =binding.tvEmail.text.toString()
           val password = binding.tvPasswordSignUp.text.toString()
           val userName = binding.tvUserName.text.toString()
           val mobileNumber = binding.tvMobileNumber.text.toString().toLong()
           val dob = binding.tvDateOfBirth.text.toString()

           if(password.isEmpty() || email.isEmpty())
           {
               Toast.makeText(this, "PlEASE ENTER THE EMAIL AND PASSWORD", Toast.LENGTH_SHORT).show()
           }
           else{
               signupo.signUp(email,password,userName,mobileNumber,dob)
               }
           }
       }
}
