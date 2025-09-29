package com.medicalhealth.healthapplication.view.adapter


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

            val isDoctorAvailable = currentDayNumber in startDayNumber..endDayNumber
            val isAvailable = isDoctorAvailable && date.isAvailable
            with(binding) {

                if (isAvailable) {
                    root.isEnabled = true
                    root.isClickable = true
                    dateContainer.alpha = 1.0f
                    dateTextView.alpha = 1.0f
                    dayOfWeekTextView.alpha = 1.0f
                    root.setOnClickListener {
                        selectedDate?.isSelected = false
                        date.isSelected = true
                        selectedDate = date
                        notifyDataSetChanged()
                        onDateClick(date)
                    }

                } else {

                    root.isEnabled = false
                    root.isClickable = false
//                    dateContainer.alpha = 0.7f
//                    dateTextView.alpha = 0.7f
//                    dayOfWeekTextView.alpha = 0.7f
                }

               dateTextView.text = date.dayOfMonth
               dayOfWeekTextView.text = date.dayOfWeek

                when {
                    !isAvailable -> {
                        dateContainer.isSelected = false
                        dateContainer.isEnabled = true
                        dateTextView.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.blueBorder
                            )
                        )
                        dayOfWeekTextView.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.blueBorder
                            )
                        )
                        dateTextView.setTextColor(ContextCompat.getColor(context, R.color.blueBorder))
                    }
                     date.isSelected -> {
                        dateContainer.isSelected = true
                        dateContainer.isEnabled = true
                        dateTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
                        dayOfWeekTextView.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )

                    }

                    else -> {

                        dateContainer.isSelected = false
                        dateContainer.isEnabled = true
                        dateTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
                        dayOfWeekTextView.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.black
                            )
                        )
                    }
                }
            }
        }
    }
}