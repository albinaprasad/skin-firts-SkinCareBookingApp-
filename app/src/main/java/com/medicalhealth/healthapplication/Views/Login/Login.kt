package com.medicalhealth.healthapplication.Views.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.R

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val button =findViewById<Button>(R.id.login)
        button.setOnClickListener {
            val intent= Intent(this, LoginActivityTwo::class.java)
            startActivity(intent)
        }

    }
}