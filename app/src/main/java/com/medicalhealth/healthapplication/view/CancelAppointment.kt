package com.medicalhealth.healthapplication.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityCancelAppointmentBinding
import com.medicalhealth.healthapplication.utils.Resource
import com.medicalhealth.healthapplication.view.fragment.AllAppointmentFragment
import com.medicalhealth.healthapplication.viewModel.SharedViewModel

class CancelAppointment : BaseActivity() {

    private lateinit var binding: ActivityCancelAppointmentBinding
    private lateinit var rgReasons: RadioGroup
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancelAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val documentId = intent.getStringExtra("BOOKING_ID")
        val id = documentId.toString()
        with(binding){
            btnCancelAppointment.setOnClickListener {
                sharedViewModel.ChangeTheStatus(id,"CANCELLED")
            }
        }
        setUpOnObserver()

    }

    private fun setUpOnObserver() {
        sharedViewModel.statusUpdateResult.observe(this){resources ->
            if(resources is Resource.Success){
                Toast.makeText(this@CancelAppointment, "Updated successfully", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }


}