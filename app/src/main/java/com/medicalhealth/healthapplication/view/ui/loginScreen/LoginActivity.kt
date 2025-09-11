package com.medicalhealth.healthapplication.view.ui.loginScreen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.medicalhealth.healthapplication.databinding.ActivityLoginBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.SetPasswordActivity
import com.medicalhealth.healthapplication.view.WelcomeScreenActivity
import com.medicalhealth.healthapplication.view.MainActivity
import com.medicalhealth.healthapplication.view.doctorScreen.DoctorsActivity
import com.medicalhealth.healthapplication.viewModel.AuthenticationViewModel
import com.medicalhealth.healthapplication.view.SignupActivity

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    val logino:AuthenticationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        setUpOnListener()
        setUpOnObserver()



    }

    private fun setUpOnObserver() {
        logino.isLogin.observe(this){
            it.onSuccess {
                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent =Intent(this@LoginActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            it.onFailure{ exception ->
                Toast.makeText(this, "{$exception.email}", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setUpOnListener() {
        with(binding){
            btnback.setOnClickListener {
                val intent = Intent(this@LoginActivity,WelcomeScreenActivity::class.java)
                startActivity(intent)
            }
            txtsignup.setOnClickListener {
                val intent = Intent(this@LoginActivity,SignupActivity::class.java)
                startActivity(intent)
            }
            txtforgotpassword.setOnClickListener {
                val intent = Intent(this@LoginActivity,SetPasswordActivity::class.java)
                startActivity(intent)
            }
            login.setOnClickListener {
                val email = tvEmailLogin.text.toString()
                val password = tvPasswordLogin.text.toString()
                if(password.isEmpty() || email.isEmpty())
                {
                    Toast.makeText(this@LoginActivity, "Please enter your credinals", Toast.LENGTH_SHORT).show()
                }
                else{
                    logino.logIn(email,password)

                }
            }

        }
        }
    }
