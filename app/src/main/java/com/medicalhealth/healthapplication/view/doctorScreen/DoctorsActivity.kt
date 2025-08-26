package com.medicalhealth.healthapplication.view.doctorScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityDoctorsBinding

class DoctorsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDoctorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var doctorList= ArrayList<Doctors>()

        doctorList.add( Doctors("Dr. Olivia Turner, M.D.", "Cardiologist", R.drawable.doctor1))
        doctorList.add(Doctors("Dr. Michael Davidson,\n M.D.", "Neurologist", R.drawable.doctor_img))
        doctorList.add(Doctors("Dr. Brown", "Pediatrician", R.drawable.doctor3))

        doctorList.add( Doctors("Dr. Sachin", "ortho", R.drawable.doctor1))
        doctorList.add(Doctors("Dr. Kohli", "psycology", R.drawable.doctor_img))
        doctorList.add(Doctors("Dr. dayle styne", "Ent", R.drawable.doctor3))


        val adapter: DoctorListViewAdapter = DoctorListViewAdapter(this,doctorList)
        binding.doctorsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.doctorsRecyclerView.adapter = adapter
    }
}