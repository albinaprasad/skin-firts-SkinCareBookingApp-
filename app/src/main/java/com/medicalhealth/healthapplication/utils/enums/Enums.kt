package com.medicalhealth.healthapplication.utils.enums

import com.medicalhealth.healthapplication.R

class Enums {
    enum class MessageType(val value: String) {
        SCHEDULES("SCHEDULES"),
        MEDICAL_NOTES("NOTES"),
        CHAT("CHATS")
    }

    enum class DisplayType(val value: Int) {
        TIME_DISPLAY(0),
        CARD_VIEW_DISPLAY(1)
    }

    enum class MenuTypes {
        HOME, MESSAGES, PROFILE, CALENDER;

        fun getSelectedIcon(): Int {
            return when (this) {
                HOME -> R.drawable.home_icon_blue
                MESSAGES -> R.drawable.chat_icon_blue
                PROFILE -> R.drawable.profile_icon_blue
                CALENDER -> R.drawable.calender_ic_blue
            }
        }

        fun getUnselectedIcon(): Int {
            return when (this) {
                HOME -> R.drawable.home_icon_white
                MESSAGES -> R.drawable.chat_icon_white
                PROFILE -> R.drawable.profile_icon_white
                CALENDER -> R.drawable.calender_icon_white
            }

        }
    }

}