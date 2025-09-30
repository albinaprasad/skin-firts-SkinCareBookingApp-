package com.medicalhealth.healthapplication.view.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ItemDoctorProfileBinding
import com.medicalhealth.healthapplication.model.data.Doctor

class DoctorAdapter(initialDoctors: List<Doctor>, private val onFavoriteClicked: (Doctor, Int) -> Unit) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    var doctors: List<Doctor> = initialDoctors
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
        val inputStream = holder.itemView.context.assets.open("doctor_images/${doctor.profileImageUrl}.png")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        holder.binding.apply {
            doctorProfilePicture.setImageBitmap(bitmap)
            doctorNameTextView.text = doctor.name
            doctorSpecializationTextView.text = doctor.specialization
            ratingTextView.text = doctor.rating.toString()
            commentCountTextView.text = doctor.commentCount.toString()

            favoriteLayout.setOnClickListener {

                onFavoriteClicked(doctor, position)
            }
            if(doctor.isFavorite){
                favoriteImageView.setImageResource(R.drawable.fav_icon_filled_darkblue)
            }else{
                favoriteImageView.setImageResource(R.drawable.favorite_image)
            }
        }
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    fun updateDate(newDoctorsList: List<Doctor>){
        this.doctors = newDoctorsList
        notifyDataSetChanged()
    }

}