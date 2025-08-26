package com.medicalhealth.healthapplication.view.doctorScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.DoctorsListCardviewBinding

class DoctorListViewAdapter(val context: Context, val DoctorDataList: ArrayList<Doctors>) :
    RecyclerView.Adapter<DoctorListViewAdapter.doctorlistViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): doctorlistViewHolder {

        val view =
            DoctorsListCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return doctorlistViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: doctorlistViewHolder,
        position: Int
    ) {
        val doctorData = DoctorDataList[position]

        holder.doctorBinding.doctorImage.setImageResource(doctorData.image)
        holder.doctorBinding.doctorName.text = doctorData.name.toString()
        holder.doctorBinding.specification.text = doctorData.specification.toString()

        holder.doctorBinding.infoButton.setOnClickListener {
            Toast("info")
        }

        holder.doctorBinding.calenderBtn.setOnClickListener {
            Toast("calender")
        }

        holder.doctorBinding.moreinfoBtn.setOnClickListener {
            Toast("info")
        }

        holder.doctorBinding.aboutwhatBtn.setOnClickListener {
            Toast("?")
        }

        holder.doctorBinding.favBtn.setOnClickListener {
            Toast("fav")
        }





    }

    override fun getItemCount(): Int {
        return DoctorDataList.size
    }


    class doctorlistViewHolder(val doctorBinding: DoctorsListCardviewBinding) :
        RecyclerView.ViewHolder(doctorBinding.root) {

    }

    fun Toast(message: String) {

        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}

