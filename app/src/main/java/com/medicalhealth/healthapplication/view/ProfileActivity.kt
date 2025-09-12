package com.medicalhealth.healthapplication.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.databinding.ActivityProfileBinding
import com.medicalhealth.healthapplication.view.adapter.MyProfileAdapter
import com.medicalhealth.healthapplication.view.fragment.BottomNavigationFragment
import com.medicalhealth.healthapplication.viewModel.ProfileViewModel

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setSelectedMenu(BottomNavigationFragment.MenuTypes.PROFILE)
    }

    private fun setUpRecyclerView(){

        with(binding) {
            val adapter = MyProfileAdapter(emptyList()){ optionSelected->
                Toast.makeText(this@ProfileActivity, optionSelected.optionName, Toast.LENGTH_SHORT).show()
            }

            profileOptionRecyclerView.layoutManager = LinearLayoutManager(this@ProfileActivity)
            profileOptionRecyclerView.adapter = adapter

            viewModel.itemOptions.observe(this@ProfileActivity){ optionsList ->
                adapter.updateData(optionsList)
            }
        }
    }
}