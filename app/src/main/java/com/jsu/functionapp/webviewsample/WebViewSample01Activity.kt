package com.jsu.functionapp.webviewsample

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
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

    @SuppressLint("SetJavaScriptEnabled")
    private fun viewInit() {

        val webUrl = intent.getStringExtra("WebUrl")

        binding.webView.settings.apply {
            javaScriptEnabled = true    //Javascript 허용
            javaScriptCanOpenWindowsAutomatically = false   //JavaScript의 window.open()동작 허용
            loadsImagesAutomatically = true //웹뷰에서 앱에 등록된 이미지 리소스를 사용하는 경우 자동으로 로드 여부
            loadWithOverviewMode = true
            useWideViewPort = false //Wide Viewport를 사용하도록 설정
            builtInZoomControls = false
            domStorageEnabled = true    //local storage 사용 여부를 설정하는 속성 (하루동안 보지않기 기능 등에 사용)
            setSupportZoom(false)
            cacheMode = WebSettings.LOAD_DEFAULT    //캐시가 만료된 경우 네트워크 사용

        }

        webUrl?.let { binding.webView.loadUrl(it) }

    }
}