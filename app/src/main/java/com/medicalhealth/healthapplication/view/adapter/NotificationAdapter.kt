package com.medicalhealth.healthapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.NotificationActivityCardDeatilsBinding
import com.medicalhealth.healthapplication.databinding.NotitificationActivityTimedisplayLayoutBinding
import com.medicalhealth.healthapplication.model.data.Notification

class NotificationAdapter(val context: Context, val notificationList: List<Notification>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    enum class DisplayType(val value: Int) {
        TIME_DISPLAY(0),
        CARD_DISPLAY(1)
    }

    enum class MessageType(val value: String) {
        SCHEDULES("SCHEDULES"),
        MEDICAL_NOTES("NOTES"),
        CHAT("CHAT")
    }

    override fun getItemViewType(position: Int): Int {
        return notificationList[position].layoutType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == DisplayType.TIME_DISPLAY.value) {
            val binding =
                NotitificationActivityTimedisplayLayoutBinding.inflate(inflater, parent, false)
            NotificationTimeDisplayViewHolder(binding)
        } else {
            val binding =
                NotificationActivityCardDeatilsBinding.inflate(inflater, parent, false)
            NotificationCardDisplayViewHolder(binding)
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val singleNotificationDetail = notificationList[position]

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

                setNotificationIcon(holder, singleNotificationDetail.messageType)

            }
        }
    }

    fun setNotificationIcon(
        holder: NotificationCardDisplayViewHolder,
        messageType: String
    ) {
        val iconResource = when (messageType) {
            MessageType.SCHEDULES.value -> R.drawable.calender_icon_thin_borders
            MessageType.MEDICAL_NOTES.value -> R.drawable.notes_icon
            MessageType.CHAT.value -> R.drawable.chat_icon_svg
            else -> R.drawable.calender_icon_thin_borders
        }

        holder.notificationCardBinding.notificationImage.setImageResource(iconResource)
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