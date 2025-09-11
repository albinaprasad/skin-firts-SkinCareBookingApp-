package com.medicalhealth.healthapplication.view.doctorScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityDoctorsBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.DoctorsList

class DoctorsActivity : BaseActivity(), DoctorsList.OnFragmentInteractionListener {
    lateinit var binding: ActivityDoctorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.titleText.text = "Doctors"



    }

    override fun onTitleChange(newTitle: String) {
        binding.titleText.text = newTitle
    }
}