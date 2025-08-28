package com.medicalhealth.healthapplication.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.medicalhealth.healthapplication.databinding.BottomNavigationLayoutBinding

class BottomNavigationFragment: Fragment() {

    private lateinit var binding: BottomNavigationLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

}