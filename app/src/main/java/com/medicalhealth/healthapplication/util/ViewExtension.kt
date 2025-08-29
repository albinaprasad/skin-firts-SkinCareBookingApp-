package com.medicalhealth.healthapplication.util

import android.view.View

object ViewExtension {

    fun View.gone(){
        this.visibility = View.GONE
    }

    fun View.show(){
        this.visibility = View.VISIBLE
    }

    fun View.selected(){
        this.isSelected = true
    }

    fun View.unselected(){
        this.isSelected = false
    }
}