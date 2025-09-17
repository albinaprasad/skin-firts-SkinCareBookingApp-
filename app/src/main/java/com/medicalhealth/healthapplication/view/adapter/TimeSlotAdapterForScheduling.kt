package com.medicalhealth.healthapplication.view.adapter

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
class TimeSlotAdapterForScheduling (
                                    private val viewModel: ScheduleCalenderViewModel
): RecyclerView.Adapter<TimeSlotAdapterForScheduling.TimeViewHolder>(){
    private lateinit var timeSlots: List<TimeSlot>

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
        timeSlots=newTimeSlots
        notifyDataSetChanged()
    }
    inner class TimeViewHolder(val binding: TimeItemBinding): RecyclerView.ViewHolder(
        binding.root){

        fun bind(item:TimeSlot){
            binding.dateTextView.text = item.timeString
            binding.root.setOnClickListener {
                viewModel.selectTimeSlot(item.time)
            }
            updateBackground(item.isSelected)
        }
        private fun updateBackground(isSelected: Boolean) {
            with(binding)
            {

                dateContainer.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.round_corner_hint_bluecolor
                )
                dateContainer.isSelected = isSelected
                dateTextView.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        if (isSelected) R.color.white else R.color.hintColor
                    )
                )
            }
        }
    }
}