package com.medicalhealth.healthapplication.view.notificationScreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.ActivityNotificationBinding
import com.medicalhealth.healthapplication.model.data.Notification
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.adapter.NotificationAdapter

class NotificationActivity : BaseActivity() {
    lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO:Replace these dummy data with real data from firebase in the future
        var notificationList: List<Notification> = listOf(
            Notification("nill", "nill", "nill", "Today", "nill", 0),

            Notification(
                "Scheduled Appointment",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "2M",
                "nill",
                "SCHEDULES",
                1
            ),

            Notification(
                "scheduled Change",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "2H",
                "nill",
                "SCHEDULES",
                1
            ),

            Notification(
                "medical notes",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "3H",
                "nill",
                "NOTES",
                1
            ),

            Notification("nill", "nill", "nill", "Yesterday", "nill", 0),

            Notification(
                "scheduled appointment",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "1D",
                "nill",
                "SCHEDULES",
                1
            ),

            Notification("nill", "nill", "nill", "15 April", "nill", 0),

            Notification(
                " medical history update",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "5D",
                "nill",
                "CHATS",
                1
            ),

            Notification(
                "scheduled Change",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "2H",
                "nill",
                "SCHEDULES",
                1
            )
        )
        val adapter: NotificationAdapter = NotificationAdapter(this, notificationList)
        binding.doctorsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.doctorsRecyclerView.adapter = adapter

    }
}