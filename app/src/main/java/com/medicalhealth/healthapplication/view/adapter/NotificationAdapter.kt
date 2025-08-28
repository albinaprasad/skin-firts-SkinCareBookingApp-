package com.medicalhealth.healthapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.NotificationActivityCardDeatilsBinding
import com.medicalhealth.healthapplication.databinding.NotitificationActivityTimedisplayLayoutBinding
import com.medicalhealth.healthapplication.model.data.Notification

class NotificationAdapter(val context: Context, val notificationList: ArrayList<Notification>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val TYPE_TIME_DISPLAY = 0
        const val TYPE_CARD_DISPLAY = 1
        const val MESSAGE_TYPE_SCHEDULES = "SCHEDULES"
        const val MESSAGE_TYPE_MEDICAL_NOTES = "NOTES"
        const val MESSAGE_TYPE_CHAT = "CHAT"
    }

    override fun getItemViewType(position: Int): Int {
        return notificationList[position].layoutType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TIME_DISPLAY -> {
                val binding =
                    NotitificationActivityTimedisplayLayoutBinding.inflate(inflater, parent, false)
                NotificationTimeDisplayViewHolder(binding)
            }

            TYPE_CARD_DISPLAY -> {
                val binding =
                    NotificationActivityCardDeatilsBinding.inflate(inflater, parent, false)
                NotificationCardDisplayViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        var singleNotificationDetail = notificationList[position]

        when (holder) {
            is NotificationTimeDisplayViewHolder -> {
                holder.notificationTimeBinding.DateTV.text = singleNotificationDetail.sectionDate
            }

            is NotificationCardDisplayViewHolder -> {
                holder.notificationCardBinding.scheduleTV.text = singleNotificationDetail.title
                holder.notificationCardBinding.dicscriptionTV.text =
                    singleNotificationDetail.description
                holder.notificationCardBinding.timeTV.text =
                    singleNotificationDetail.notificationTime

                when (singleNotificationDetail.messageType) {

                    MESSAGE_TYPE_SCHEDULES -> holder.notificationCardBinding.notificationImage.setImageResource(
                        R.drawable.calender_icon_thin_borders
                    )

                    MESSAGE_TYPE_MEDICAL_NOTES -> holder.notificationCardBinding.notificationImage.setImageResource(
                        R.drawable.notes_icon
                    )

                    MESSAGE_TYPE_CHAT -> holder.notificationCardBinding.notificationImage.setImageResource(
                        R.drawable.chat_icon_svg
                    )

                    else -> holder.notificationCardBinding.notificationImage.setImageResource(
                        R.drawable.calender_icon_thin_borders
                    )
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
    class NotificationTimeDisplayViewHolder(val notificationTimeBinding: NotitificationActivityTimedisplayLayoutBinding) :
        RecyclerView.ViewHolder(notificationTimeBinding.root) {

    }
    class NotificationCardDisplayViewHolder(val notificationCardBinding: NotificationActivityCardDeatilsBinding) :
        RecyclerView.ViewHolder(notificationCardBinding.root) {

    }
}