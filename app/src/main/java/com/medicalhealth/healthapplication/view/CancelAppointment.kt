package com.medicalhealth.healthapplication.view

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityCancelAppointmentBinding

class CancelAppointment : AppCompatActivity() {
    private lateinit var binding: ActivityCancelAppointmentBinding
    private lateinit var item: List<RadioButton>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCancelAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding){
            item = listOf(optionRescheduling,optionWeather,optionUnexpected,optionOthers)
            item.forEach { radioButton ->
                radioButton.setOnClickListener{
                    handleTheColor(radioButton)
                }

            }
        }
        }

    fun handleTheColor(selectedButton: RadioButton) {
        item.forEach { radioButton ->
            if(radioButton != selectedButton){
                 val parentCardView: View?= radioButton.parent.parent.parent as? CardView
                parentCardView?.isSelected = false
                radioButton.isSelected = false

            }
            else{
                val parentCardView: View?= selectedButton.parent.parent.parent as? CardView
                parentCardView?.isSelected = true
                radioButton.isSelected = true
            }
        }
    }
}
