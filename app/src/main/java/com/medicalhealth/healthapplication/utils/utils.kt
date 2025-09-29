package com.medicalhealth.healthapplication.utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    fun convertDateToDayMonthFormat(dateString: String): String {

        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())


        val date: Date = try {

            inputFormat.parse(dateString) ?: return "Unknown Date"
        } catch (e: Exception) {

            android.util.Log.e("DateUtils", "Error parsing date: $dateString", e)
            return "Invalid Date Format"
        }


        val outputFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())


        return outputFormat.format(date)
    }
}