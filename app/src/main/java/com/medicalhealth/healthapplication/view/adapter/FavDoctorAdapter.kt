package com.medicalhealth.healthapplication.view.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.FavDoctorsCardviewBinding
import com.medicalhealth.healthapplication.model.data.Doctor

class FavDoctorAdapter( private val makeAppointmentClickListener:(Doctor) -> Unit):
    RecyclerView.Adapter<FavDoctorAdapter.FavDoctorViewHolder>() {

    private var doctors: List<Doctor> = emptyList()

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
        val inputStream = holder.itemView.context.assets.open("doctor_images/${doctor.profileImageUrl}.png")
        val bitmap = BitmapFactory.decodeStream(inputStream)

       with(holder.favBinding){
           doctorImage.setImageBitmap(bitmap)
           nameTV.text = doctor.name
           specificationTV.text = doctor.specialization
           favBtn.setImageResource(R.drawable.fav_filled)

           AppointementBtn.setOnClickListener {
               makeAppointmentClickListener(doctor)
           }
       }
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    class FavDoctorViewHolder(val favBinding: FavDoctorsCardviewBinding) :
        RecyclerView.ViewHolder(favBinding.root)


    fun updateData(newDoctors: List<Doctor>) {
        this.doctors = newDoctors
        notifyDataSetChanged()
    }
}