package com.medicalhealth.healthapplication.view.adapter

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.TimeItemBinding
import com.medicalhealth.healthapplication.model.data.TimeSlot
import com.medicalhealth.healthapplication.viewModel.ScheduleCalenderViewModel

@RequiresApi(Build.VERSION_CODES.O)
class TimeSlotAdapterForScheduling(
    private val viewModel: ScheduleCalenderViewModel
) : RecyclerView.Adapter<TimeSlotAdapterForScheduling.TimeViewHolder>() {
    private var timeSlots: MutableList<TimeSlot> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeViewHolder {
        val view = TimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TimeViewHolder,
        position: Int
    ) {
        holder.bind(timeSlots[position])
    }

    override fun getItemCount(): Int {
      return timeSlots.size
    }
     fun updateTimeSlots(
        newTimeSlots: List<TimeSlot>
    ) {
         timeSlots.clear()
         timeSlots.addAll(newTimeSlots)
         notifyDataSetChanged()
    }
    inner class TimeViewHolder(val binding: TimeItemBinding): RecyclerView.ViewHolder(
        binding.root) {

        fun bind(item: TimeSlot) {
            binding.dateTextView.text = item.timeString
            binding.root.setOnClickListener {
                if (item.isAvailable) {
                    viewModel.selectTimeSlot(item.time)
                }
            }
            applySlotStyling(item)
        }

        fun applySlotStyling(item: TimeSlot)
        {
            with(binding) {
                val context = binding.root.context
                when {
                    !item.isAvailable -> {

                        dateContainer.background = createRoundedBackground(
                            ContextCompat.getColor(context, R.color.off_blue)
                        )
                        dateTextView.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.hintColor
                            )
                        )
                        root.isClickable = false
                        root.alpha = 0.9f


                    }

                    item.isSelected -> {

                        dateContainer.background = createRoundedBackground(
                            ContextCompat.getColor(context, R.color.backgroundColor)
                        )
                        dateTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                        root.isClickable = true
                        root.alpha = 1.0f

                    }

                    else -> {

                        dateContainer.background = createRoundedBackground(
                            ContextCompat.getColor(context, R.color.off_blue)
                        )
                        dateTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                        root.isClickable = true
                        root.alpha = 1.0f

                    }

                }
            }
        }
    }
    private fun createRoundedBackground(color: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(color)
            cornerRadius = 40f
        }
    }

}