package com.medicalhealth.healthapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FavDoctorsCardviewBinding
import com.medicalhealth.healthapplication.databinding.RatingCardviewBinding
import com.medicalhealth.healthapplication.model.data.Doctor

class FavDoctorAdapter(private val doctors: List<Doctor>) :
    RecyclerView.Adapter<FavDoctorAdapter.FavDoctorViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavDoctorViewHolder {
        val view = FavDoctorsCardviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        return FavDoctorViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FavDoctorViewHolder,
        position: Int
    ) {
        val doctor = doctors[position]
        holder.favBinding.apply {
            doctorImage.setImageResource(doctor.profileImageUrl)
            nameTV.text = doctor.name
            specificationTV.text = doctor.specialization
            favBtn.setImageResource(R.drawable.fav_filled)
        }
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    class FavDoctorViewHolder(val favBinding: FavDoctorsCardviewBinding) :
        RecyclerView.ViewHolder(favBinding.root)
}