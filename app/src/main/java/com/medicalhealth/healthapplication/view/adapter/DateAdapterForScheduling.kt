package com.medicalhealth.healthapplication.view.adapter

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ItemDateBinding
import com.medicalhealth.healthapplication.model.data.Date
import com.medicalhealth.healthapplication.model.data.Doctor
import com.medicalhealth.healthapplication.utils.enums.Enums
import java.util.Calendar

class DateAdapterForScheduling(
    var dateList: MutableList<Date>,
    private val doctorObj: Doctor,
    private val onDateClick: (Date) -> Unit
) : RecyclerView.Adapter<DateAdapterForScheduling.DateViewHolder>() {

    private var selectedDate: Date? = null

    fun updateDates(newDateList: List<Date>) {
        dateList.clear()
        dateList.addAll(newDateList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = ItemDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val dateAtThisPosition = dateList[position]
        holder.bind(dateAtThisPosition)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    inner class DateViewHolder(val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Date) {
            val context = binding.root.context


            val currentDay = date.dayOfWeek.uppercase()
            val currentDayNumber = Enums.DayOfWeek.entries.find {
                it.name == currentDay
            }?.calendarNumber ?: Enums.DayOfWeek.MON.calendarNumber

            val startDayNumber = if (doctorObj.startDay in 1..7) doctorObj.startDay else Calendar.MONDAY
            val endDayNumber = if (doctorObj.endDay in 1..7) doctorObj.endDay else Calendar.SATURDAY

            val isAvailable = currentDayNumber in startDayNumber..endDayNumber

            if (isAvailable) {
                binding.root.isEnabled = true
                binding.root.isClickable = true
                binding.dateContainer.alpha = 1.0f
                binding.dateTextView.alpha = 1.0f
                binding.dayOfWeekTextView.alpha = 1.0f
                binding.root.setOnClickListener {
                    selectedDate?.isSelected = false
                    date.isSelected = true
                    selectedDate = date
                    notifyDataSetChanged()
                    onDateClick(date)
                }
            } else {
                binding.root.isEnabled = false
                binding.root.isClickable = false
                binding.dateContainer.alpha = 0.3f
                binding.dateTextView.alpha = 0.3f
                binding.dayOfWeekTextView.alpha = 0.3f
                binding.root.setOnClickListener(null)
            }

            binding.dateTextView.text = date.dayOfMonth
            binding.dayOfWeekTextView.text = date.dayOfWeek

            when {
                !isAvailable -> {

                    binding.dateContainer.isSelected = false
                    binding.dateContainer.isEnabled = true
                    binding.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.hintColor))
                    binding.dayOfWeekTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                    binding.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
                date.isToday || date.isSelected -> {

                    binding.dateContainer.isSelected = true
                    binding.dateContainer.isEnabled = true
                    binding.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                    binding.dayOfWeekTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                else -> {

                    binding.dateContainer.isSelected = false
                    binding.dateContainer.isEnabled = true
                    binding.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                    binding.dayOfWeekTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }
        }
    }
}