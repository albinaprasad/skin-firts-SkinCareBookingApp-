package com.medicalhealth.healthapplication.view.ui.loginScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityLoginBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.SetPasswordActivity
import com.medicalhealth.healthapplication.view.SignupActivity
import com.medicalhealth.healthapplication.view.WelcomeScreenActivity
import com.medicalhealth.healthapplication.view.MainActivity

class LoginActivity : BaseActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

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
        }

    }
}