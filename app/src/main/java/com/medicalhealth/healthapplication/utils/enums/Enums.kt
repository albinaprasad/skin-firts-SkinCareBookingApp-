package com.medicalhealth.healthapplication.utils.enums

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

    enum class NavigationDestination{HOME, CHAT, PROFILE, CALENDAR}

}