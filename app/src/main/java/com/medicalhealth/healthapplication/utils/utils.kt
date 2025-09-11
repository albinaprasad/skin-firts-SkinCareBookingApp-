package com.medicalhealth.healthapplication.utils

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat


object utils {

    inline fun WindowInsetsCompat.getSystemBarInsets(view: View, action: (insets: Insets) -> Unit): WindowInsetsCompat {
        val systemBarInsets = getInsets(WindowInsetsCompat.Type.systemBars())
        action(systemBarInsets)
        view.requestLayout() // update layout added for a bug fix( when resumes the screen insets values becomes 0 and then gets original value, but layout updated with 0. so layout updation performed
        return this
    }
}