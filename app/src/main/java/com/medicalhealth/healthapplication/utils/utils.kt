package com.medicalhealth.healthapplication.utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

    fun getBitmapFromAssets(context: Context, imageName: String): Bitmap? {
        return try {
            BitmapFactory.decodeStream(context.assets.open("${Constants.DOCTOR_IMAGES_PATH}/${imageName}.png"))
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}