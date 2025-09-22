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
            updateBackground(item.isSelected, item.isAvailable)
        }

        private fun updateBackground(isSelected: Boolean, isAvailable: Boolean) {
            with(binding)
            {
                val context = itemView.context
                when {
                    // UNAVAILABLE (booked)
                    !isAvailable -> {
                        dateContainer.background = createRoundedBackground(
                            ContextCompat.getColor(context, R.color.off_blue),
                        )

                        dateTextView.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.black
                            )
                        )

                        dateContainer.alpha = 0.5f
                        itemView.isEnabled = false
                    }

                    // SELECTED & AVAILABLE
                    isSelected -> {

                        dateContainer.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.dark_blue_round_corner
                        )
                        dateTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                        dateContainer.alpha = 1.0f
                        itemView.isEnabled = true
                    }

                    // AVAILABLE BUT NOT SELECTED
                    else -> {
                        // Default available style (not selected)
                        dateContainer.background = ContextCompat.getDrawable(
                            context,
                            R.drawable.white_background_with_stroke
                        )
                        dateTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                        dateContainer.alpha = 1.0f
                        itemView.isEnabled = true
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
}