package com.medicalhealth.healthapplication.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.medicalhealth.healthapplication.R

class PrivacyPolicyActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val webView: WebView = findViewById(R.id.privacyPolicyWebView)

        // Set a WebViewClient to keep the navigation within your app
        webView.webViewClient = WebViewClient()


        webView.loadUrl("file:///android_asset/Privacy_Policy/terms_and_condition.html")
    }

    override fun onBackPressed() {
        if (findViewById<WebView>(R.id.privacyPolicyWebView).canGoBack()) {
            findViewById<WebView>(R.id.privacyPolicyWebView).goBack()
        } else {
            super.onBackPressed()
        }
    }
}
