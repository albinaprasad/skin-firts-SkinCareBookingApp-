package com.medicalhealth.healthapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ItemDoctorProfileBinding
import com.medicalhealth.healthapplication.model.data.Doctor

class DoctorAdapter(private var doctors: List<Doctor>, private val onFavoriteClicked: (Doctor) -> Unit) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(val binding: ItemDoctorProfileBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorAdapter.DoctorViewHolder {
         val binding = ItemDoctorProfileBinding.inflate(
             LayoutInflater.from(parent.context),
             parent, false
         )
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorAdapter.DoctorViewHolder, position: Int) {
         val doctor = doctors[position]
        holder.binding.apply {
            doctorProfilePicture.setImageResource(doctor.profileImageUrl)
            doctorNameTextView.text = doctor.name
            doctorSpecializationTextView.text = doctor.specialization
            ratingTextView.text = doctor.rating.toString()
            commentCountTextView.text = doctor.commentCount.toString()

            favoriteImageView.setImageResource(
                if(doctor.isFavorite) R.drawable.favorite_filled else R.drawable.favorite_image
            )

            favoriteLayout.setOnClickListener {
                onFavoriteClicked(doctor)
            }
        }
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    fun updateDate(newDoctorsList: List<Doctor>){
        doctors = newDoctorsList
        notifyDataSetChanged()
    }
}