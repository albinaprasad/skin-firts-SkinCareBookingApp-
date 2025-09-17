package com.medicalhealth.healthapplication.viewModel

import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.model.data.Doctor

class SharedViewModel: ViewModel() {


    private val _selectedDoctor = MutableLiveData<Doctor>()
    val selectedDoctor: LiveData<Doctor> = _selectedDoctor

    private val _titleChange = MutableLiveData<String>()
    val titleChange:LiveData<String> = _titleChange


    fun selectDoctor(doctor: Doctor) {
        _selectedDoctor.value = doctor
    }
    fun setTitle(title:String){
        _titleChange.value = title
    }

    fun updateButtons(
        fragmentName: String,
        ratingBtn: ImageButton,
        sortButton: Button,
        favBtn: ImageButton,
    ) {
        resetAllButtons(ratingBtn, sortButton, favBtn)

        when (fragmentName) {
            "ratingFragment" -> {
                ratingBtn.setImageResource(R.drawable.star_icon_white)
                ratingBtn.setBackgroundResource(R.drawable.background_ellipse_blue)
            }

            "DoctorListFragment" -> {
                sortButton.backgroundTintList =
                    ContextCompat.getColorStateList(sortButton.context, R.color.button_blue)
                sortButton.setTextColor(ContextCompat.getColor(sortButton.context, R.color.white))
            }

            "FavouriteFragment" -> {
                favBtn.setImageResource(R.drawable.fav_white_filled)
                favBtn.setBackgroundResource(R.drawable.background_ellipse_blue)
            }
        }
    }
    fun resetAllButtons(
        ratingBtn: ImageButton, sortButton: Button, favBtn: ImageButton
    ) {
        sortButton.backgroundTintList =
            ContextCompat.getColorStateList(sortButton.context, R.color.off_blue)
        sortButton.setTextColor(ContextCompat.getColor(sortButton.context, R.color.button_blue))
        ratingBtn.setImageResource(R.drawable.star_icon)
        ratingBtn.setBackgroundResource(R.drawable.round_button_offblue)
        favBtn.setImageResource(R.drawable.fav_icon)
        favBtn.setBackgroundResource(R.drawable.round_button_offblue)
    }
}
