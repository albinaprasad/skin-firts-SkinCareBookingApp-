package com.medicalhealth.healthapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewViewModel: ViewModel() {
    private var _isFavorite = MutableLiveData(true)
    val isFavorite: LiveData<Boolean> = _isFavorite

    private var _ratingNumber = MutableLiveData(0)
    val ratingNumber: LiveData<Int> = _ratingNumber

    fun toggleFavoriteButton(){
        _isFavorite.value = !(_isFavorite.value ?: false)
    }

    fun setRating(ratingNumber: Int){
        _ratingNumber.value = ratingNumber
    }
}