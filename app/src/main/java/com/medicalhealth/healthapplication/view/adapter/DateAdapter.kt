package com.medicalhealth.healthapplication.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ItemDateBinding
import com.medicalhealth.healthapplication.model.data.Date

class DateAdapter(private var dates: List<Date>, private val onDateSelected: (Date) -> Unit) :
    RecyclerView.Adapter<DateAdapter.DateViewHolder>() {
    class DateViewHolder(val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateAdapter.DateViewHolder {
        val binding = ItemDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateAdapter.DateViewHolder, position: Int) {
        val date = dates[position]

        with(holder.binding){
            dateTextView.text = date.dayOfMonth
            dayOfWeekTextView.text = date.dayOfWeek
            dateContainer.isSelected = date.isSelected
            dateTextView.isSelected = date.isSelected
            dayOfWeekTextView.isSelected = date.isSelected
        }


        holder.itemView.setOnClickListener{
            onDateSelected(date)
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    fun updateData(newOptionList: List<Date>){
        dates = newOptionList
        notifyDataSetChanged()
    }
}