package com.medicalhealth.healthapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityCancelAppointmentBinding
import com.medicalhealth.healthapplication.view.fragment.AllAppointmentFragment
import com.medicalhealth.healthapplication.viewModel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CancelAppointment : BaseActivity() {

    private lateinit var binding: ActivityCancelAppointmentBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancelAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val documentId = intent.getStringExtra("BOOKING_ID")
        val id = documentId.toString()
        with(binding){
            btnCancelAppointment.setOnClickListener {
                Log.d("CancelFlow", "jhdfhdijk")
                sharedViewModel.changeTheStatus(id,"CANCELLED")
                finish()

            }
            }
        }


    }
