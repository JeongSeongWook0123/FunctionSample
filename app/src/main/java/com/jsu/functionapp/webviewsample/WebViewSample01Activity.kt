package com.jsu.functionapp.webviewsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import com.jsu.functionapp.BaseVBActivity
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ActivityWebViewSample01Binding

class WebViewSample01Activity : BaseVBActivity() {
    private val activity = this
    private lateinit var binding: ActivityWebViewSample01Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewSample01Binding.inflate(layoutInflater)
        setContentView(binding.root)
        viewInit()
    }

    private fun viewInit() {

        val webUrl = intent.getStringExtra("WebUrl")

        webUrl?.let { binding.wvUrl.loadUrl(it) }

        binding.wvUrl.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = false
            builtInZoomControls = false
            domStorageEnabled = true
            setSupportZoom(false)
            cacheMode = WebSettings.LOAD_DEFAULT

        }

    }
}