package com.medicalhealth.healthapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.DoctorsListCardviewBinding
import com.medicalhealth.healthapplication.model.data.Doctor


class DoctorListViewAdapter(
    private val context: Context,
    private var dataList: List<Doctor>,
    private val onInfoButtonClick:(Doctor) -> Unit
) : RecyclerView.Adapter<DoctorListViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            DoctorsListCardviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val currentItem = dataList[position]

        // Bind data to the views using the binding object
        holder.binding.doctorImage.setImageResource(currentItem.profileImageUrl)
        holder.binding.doctorName.text = currentItem.name
        holder.binding.specification.text = currentItem.specialization


        holder.binding.infoButton.setOnClickListener {
            onInfoButtonClick(currentItem)

        }

        holder.binding.calenderBtn.setOnClickListener {
            showToast(context, "calender")
        }

        holder.binding.moreinfoBtn.setOnClickListener {
            showToast(context, "info")
        }

        holder.binding.aboutwhatBtn.setOnClickListener {
            showToast(context, "?")
        }

        holder.binding.favBtn.setOnClickListener {
            showToast(context, "fav")
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class MyViewHolder(val binding: DoctorsListCardviewBinding) :
        RecyclerView.ViewHolder(binding.root)


    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun updateData(newData: List<Doctor>) {
        dataList = newData
        notifyDataSetChanged()
    }


}