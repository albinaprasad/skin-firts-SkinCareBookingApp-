package com.medicalhealth.healthapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.R
import com.medicalhealth.healthapplication.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : BaseActivity() {
    private lateinit var binding:ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backbtnPrivacyPolicy.setOnClickListener {
            finish()
        }

        // Set a WebViewClient to keep the navigation within your app
        binding.privacyPolicyWebView.webViewClient = WebViewClient()


        binding.privacyPolicyWebView.loadUrl("file:///android_asset/Privacy_Policy/terms_and_condition.html")
    }

    override fun onBackPressed() {
        if (binding.privacyPolicyWebView.canGoBack()) {
            binding.privacyPolicyWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
