package com.medicalhealth.healthapplication.view


import android.os.Bundle
import android.webkit.WebViewClient
import com.medicalhealth.healthapplication.databinding.ActivityPrivacyPolicyBinding

class PrivacyPolicyActivity : BaseActivity() {
    private lateinit var binding: ActivityPrivacyPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.privacyPolicyWebView.webViewClient = WebViewClient()
        binding.privacyPolicyWebView.loadUrl("file:///android_asset/Privacy_Policy/terms_and_condition.html")
        setUpOnListener()
    }

    private fun setUpOnListener() {
        with(binding) {
            backbtnPrivacyPolicy.setOnClickListener {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (binding.privacyPolicyWebView.canGoBack()) {
            binding.privacyPolicyWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
