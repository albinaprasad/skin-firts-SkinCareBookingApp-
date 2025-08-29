package com.medicalhealth.healthapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.databinding.ItemDateBinding
import com.medicalhealth.healthapplication.model.data.Date

class DateAdapter(private val dates: List<Date>, private val onDateSelected: (Date) -> Unit) :
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
        holder.binding.dateTextView.text = date.dayOfMonth
        holder.binding.dayOfWeekTextView.text = date.dayOfWeek

        holder.binding.dateContainer.isSelected = date.isSelected
        holder.binding.dateTextView.isSelected = date.isSelected
        holder.binding.dayOfWeekTextView.isSelected = date.isSelected

        holder.itemView.setOnClickListener{
            onDateSelected(date)
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }
}