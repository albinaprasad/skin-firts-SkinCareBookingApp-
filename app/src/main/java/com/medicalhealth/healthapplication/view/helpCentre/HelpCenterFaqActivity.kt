package com.medicalhealth.healthapplication.view.helpCentre

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityHelpCenterFaqBinding
import com.medicalhealth.healthapplication.view.BaseActivity
import com.medicalhealth.healthapplication.view.homeScreen.MainActivity
import com.medicalhealth.healthapplication.view.profileScreen.EditProfileActivity

class HelpCenterFaqActivity : BaseActivity() {
    lateinit var binding: ActivityHelpCenterFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityHelpCenterFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupButtonClickListeners()
    }
    fun setupButtonClickListeners(){

        with(binding){
            contactBtn.setOnClickListener {
                val faqIntent= Intent(this@HelpCenterFaqActivity, HelpCenterActivity::class.java)
                startActivity(faqIntent)
                finish()
            }
            backImageBtn.setOnClickListener {
                val intent= Intent(this@HelpCenterFaqActivity, MainActivity::class.java)
                intent.putExtra(MainActivity.FRAGMENT_TO_LOAD_KEY, "profile")
                startActivity(intent)
                finish()
            }
        }
    }
}