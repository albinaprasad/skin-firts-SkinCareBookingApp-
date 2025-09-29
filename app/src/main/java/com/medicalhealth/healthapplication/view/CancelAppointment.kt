package com.medicalhealth.healthapplication.view

import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityCancelAppointmentBinding
import com.medicalhealth.healthapplication.viewModel.SharedViewModel

class CancelAppointment : BaseActivity() {

    private lateinit var binding: ActivityCancelAppointmentBinding
    private lateinit var rgReasons: RadioGroup
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancelAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val documentId = intent.getStringExtra("id")
        val id = documentId.toString()
        with(binding){
            btnCancelAppointment.setOnClickListener {
                sharedViewModel.ChangeTheStatus(id,"CANCELLED")
            }
        }

    }


}