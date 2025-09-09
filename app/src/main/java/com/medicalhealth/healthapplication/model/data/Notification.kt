package com.medicalhealth.healthapplication.model.data

data class Notification(
    val title: String,
    val description: String,
    val notificationTime: String,
    val sectionDate: String,
    val messageType: String,
    val layoutType: Int
) {
    // the layout type =0 means  the  notification_timedisplay_layout
    //the layout type=1 means the notification_cardview layout

    //sectionDate is used to show the date on the heading in the 2nd layout,
    //notificationTime is used to show teh time at which teh notification arrived
    //the messageType  is used to identify the different type of messages for notification("SCHEDULES",
    //      "NOTES","CHAT")


}