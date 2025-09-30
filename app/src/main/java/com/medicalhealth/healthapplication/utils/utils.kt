package com.medicalhealth.healthapplication.utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object utils {
    inline fun WindowInsetsCompat.getSystemBarInsets(
        view: View,
        action: (insets: Insets) -> Unit
    ): WindowInsetsCompat {
        val systemBarInsets = getInsets(WindowInsetsCompat.Type.systemBars())
        action(systemBarInsets)
        view.requestLayout() // update layout added for a bug fix( when resumes the screen insets values becomes 0 and then gets original value, but layout updated with 0. so layout updation performed
        return this
    }

    fun getBitmapFromAssets(context: Context, imageName: String): Bitmap? {
        return try {
            BitmapFactory.decodeStream(context.assets.open("${Constants.DOCTOR_IMAGES_PATH}/${imageName}.png"))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun convertDateToLongFormat(dateString: String): String {
        // 1. Define the input format (yyyy-M-d)
        // 'yyyy': Year, 'M': 1 or 2-digit month, 'd': 1 or 2-digit day
        val inputFormat = SimpleDateFormat("yyyy-M-d", Locale.US)

        // 2. Define the desired output format (MMMM d, EEEE)
        // 'MMMM': Full month name (September)
        // 'd': Day of the month (28)
        // 'EEEE': Full day of the week (Sunday)
        // Using Locale.US ensures the month and day names are in English.
        val outputFormat = SimpleDateFormat("EEEE,d MMMM ", Locale.US)


        return try {
            // Parse the input string into a Date object
            val date: Date? = inputFormat.parse(dateString)
            Log.d("mathews", "convertDateToLongFormat: ${date?.let { outputFormat.format(it) }}")

            // Format the Date object into the desired output string
            if (date != null) {
                outputFormat.format(date)
            } else {
                "Invalid Date"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            "Error parsing date: ${e.message}"
        }
    }
    fun addThirtyMinutes(timeString: String): String {
        val formatter = SimpleDateFormat("hh:mm a", Locale.US)
        try {
            val date: java.util.Date? = formatter.parse(timeString)
            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.add(Calendar.MINUTE, 30)
                return formatter.format(calendar.time)
            }
            return "Parsing failed"
        } catch (e: Exception) {
            return "Error"
        }
    }
}


