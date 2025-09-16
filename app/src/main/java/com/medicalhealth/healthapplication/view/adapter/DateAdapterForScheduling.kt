package com.medicalhealth.healthapplication.view.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ItemDateBinding
import com.medicalhealth.healthapplication.model.data.Date

class DateAdapterForScheduling(var dateList: MutableList<Date>): RecyclerView.Adapter<DateAdapterForScheduling.DateViewHolder>() {


    fun updateDates(newDateList: List<Date>) {
        dateList.clear()
        dateList.addAll(newDateList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DateViewHolder {
        val view = ItemDateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DateViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: DateViewHolder,
        position: Int
    ) {
        val dateAtThisPosition = dateList[position]
        holder.bind(dateAtThisPosition)
    }

    override fun getItemCount(): Int {
      return dateList.size
    }

    inner class DateViewHolder(val binding: ItemDateBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Date) {
            binding.dateTextView.text = date.dayOfMonth
            binding.dayOfWeekTextView.text = date.dayOfWeek
            val context = binding.root.context
            when {
                date.isToday -> {
                    // Create curved background for today's date
                    val todayDrawable = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        setColor(ContextCompat.getColor(context, R.color.backgroundColor))
                        cornerRadius = 40f // Curved corners
                    }
                    binding.dateContainer.background = todayDrawable

                    // White text for today
                    binding.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                    binding.dayOfWeekTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                }

                date.isSelected -> {

                    val selectedDrawable = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        setColor(ContextCompat.getColor(context, R.color.white))
                        cornerRadius = 40f // Curved corners
                    }
                    binding.dateContainer.background = selectedDrawable


                    binding.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                    binding.dayOfWeekTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                }

                else -> {

                    val selectedDrawable = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        setColor(ContextCompat.getColor(context, R.color.white))
                        cornerRadius = 40f // Curved corners
                    }
                    binding.dateContainer.background = selectedDrawable


                    binding.dateTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                    binding.dayOfWeekTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }
        }

    }
}