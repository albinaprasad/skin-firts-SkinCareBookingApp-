package com.medicalhealth.healthapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.medicalhealth.healthapplication.databinding.RatingCardviewBinding
import com.medicalhealth.healthapplication.model.data.Doctor

class RatingsAdapter( ) :
    RecyclerView.Adapter<RatingsAdapter.RatingViewHolder>() {

    lateinit var doctors: List<Doctor>

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

    fun updateData(newDoctors: List<Doctor>) {
        this.doctors = newDoctors
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(
        holder: RatingViewHolder,
        position: Int
    ) {
        val doctor = doctors[position]
        holder.ratingBinding.apply {
            doctorImage.setImageResource(doctor.profileImageUrl)
            nameTV.text = doctor.name
            specificationTV.text = doctor.specialization
            ratingTV.text = doctor.rating.toString()
        }
    }

    override fun getItemCount(): Int {

        return doctors.size
    }


    class RatingViewHolder(val ratingBinding: RatingCardviewBinding) :
        RecyclerView.ViewHolder(ratingBinding.root)
}