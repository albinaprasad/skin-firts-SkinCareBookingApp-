package com.medicalhealth.healthapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.ActivityRatingBinding
import com.medicalhealth.healthapplication.databinding.ItemDoctorProfileBinding
import com.medicalhealth.healthapplication.databinding.RatingCardviewBinding
import com.medicalhealth.healthapplication.model.data.Doctor

class RatingsAdapter(private val doctors: List<Doctor>) :
    RecyclerView.Adapter<RatingsAdapter.RatingViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RatingViewHolder {
        val view = RatingCardviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return RatingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RatingViewHolder,
        position: Int
    ) {
        val doctor=doctors[position]
        holder.ratingBinding.apply {
            doctorImage.setImageResource(doctor.profileImageUrl)
            nameTV.text=doctor.name
            specificationTV.text=doctor.specialization
            ratingTV.text=doctor.rating.toString()
        }
    }

    override fun getItemCount(): Int {

        return doctors.size
    }


    class RatingViewHolder(val ratingBinding: RatingCardviewBinding) :
        RecyclerView.ViewHolder(ratingBinding.root)
}