package com.medicalhealth.healthapplication.view.helpCentre

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.databinding.ActivityHelpCenterBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.adapter.HelpCenterAdapter
import com.medicalhealth.healthapplication.view.homeScreen.MainActivity
import com.medicalhealth.healthapplication.viewModel.HelpCenterViewModel

class HelpCenterActivity : BaseActivity() {
    private lateinit var binding: ActivityHelpCenterBinding
    private val viewModel: HelpCenterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHelpCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpRecyclerView()
        buttonClickListeners()
    }

    private fun setUpRecyclerView(){
        with(binding){
            val adapter = HelpCenterAdapter(emptyList()) { optionSelected ->
                Toast.makeText(
                    this@HelpCenterActivity,
                    optionSelected.optionName,
                    Toast.LENGTH_SHORT
                ).show()
            }
            helpCenterRecyclerView.adapter = adapter
            viewModel.itemOptions.observe(this@HelpCenterActivity){ optionList ->
                adapter.updateData(optionList)
            }
        }
    }

    fun buttonClickListeners(){
        with(binding){
            faqBtn.setOnClickListener {
                val intent= Intent(this@HelpCenterActivity, HelpCenterFaqActivity::class.java)
                startActivity(intent)
                finish()
            }
            backImageBtn.setOnClickListener {
                val intent= Intent(this@HelpCenterActivity, MainActivity::class.java)
                intent.putExtra(MainActivity.FRAGMENT_TO_LOAD_KEY, "profile")
                startActivity(intent)
                finish()
            }
        }
    }
}