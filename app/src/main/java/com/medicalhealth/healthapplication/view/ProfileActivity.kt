package com.medicalhealth.healthapplication.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityProfileBinding
import com.medicalhealth.healthapplication.view.adapter.MyProfileAdapter
import com.medicalhealth.healthapplication.view.fragment.BottomNavigationFragment
import com.medicalhealth.healthapplication.viewModel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            profileOptionRecyclerView.layoutManager = LinearLayoutManager(this@ProfileActivity)
            viewModel.profileOptions.observe(this@ProfileActivity) { optionsList->
                val adapter = MyProfileAdapter(optionsList){ optionSelected ->
                    Toast.makeText(this@ProfileActivity, optionSelected.optionName, Toast.LENGTH_SHORT).show()
                }
                profileOptionRecyclerView.adapter = adapter
            }
        }
    }
}