package com.medicalhealth.healthapplication.view


import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun showToastMessage(message:String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }


}
