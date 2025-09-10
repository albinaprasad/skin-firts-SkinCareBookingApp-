package com.medicalhealth.healthapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.DoctorsListCardviewBinding
import com.medicalhealth.healthapplication.model.data.Doctor

class DoctorListViewAdapter(val context: Context, val doctorDataList: ArrayList<Doctor>) :
    RecyclerView.Adapter<DoctorListViewAdapter.DoctorlistViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorlistViewHolder {

        val view =
            DoctorsListCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorlistViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DoctorlistViewHolder,
        position: Int
    ) {
        val doctorData = doctorDataList[position]

        holder.doctorBinding.doctorImage.setImageResource(doctorData.profileImageUrl)
        holder.doctorBinding.doctorName.text = doctorData.name
        holder.doctorBinding.specification.text = doctorData.specialization

        holder.doctorBinding.infoButton.setOnClickListener {
            showToast("info")
        }

        holder.doctorBinding.calenderBtn.setOnClickListener {
            showToast("calender")
        }

        holder.doctorBinding.moreinfoBtn.setOnClickListener {
            showToast("info")
        }

        holder.doctorBinding.aboutwhatBtn.setOnClickListener {
            showToast("?")
        }

        holder.doctorBinding.favBtn.setOnClickListener {
            showToast("fav")
        }
    }

    override fun getItemCount(): Int {
        return doctorDataList.size
    }

    class DoctorlistViewHolder(val doctorBinding: DoctorsListCardviewBinding) :
        RecyclerView.ViewHolder(doctorBinding.root) {
    }
    fun showToast(message: String) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}