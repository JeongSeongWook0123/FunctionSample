package com.jsu.functionapp.webview_sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import com.jsu.functionapp.BaseVBActivity
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
            javaScriptEnabled = true    //Javascript 허용 (defalut: false)
            javaScriptCanOpenWindowsAutomatically = false   //JavaScript의 window.open()동작 허용  (defalut: false)
            loadsImagesAutomatically = true //웹뷰에서 앱에 등록된 이미지 리소스를 사용하는 경우 자동으로 로드 여부  (defalut: true)
            loadWithOverviewMode = true //페이지를 전체화면에 맞추는 모드를 활성화  (defalut: false)
            useWideViewPort = false     //Wide Viewport를 사용하도록 설정  (defalut: true)
            builtInZoomControls = false //Zoom Control 활성화 여부, 확대/축소 제스처 사용  (defalut: false)
            domStorageEnabled = true    //local storage 사용 여부를 설정하는 속성 (하루동안 보지않기 기능 등에 사용)  (defalut: false)
            setSupportZoom(false)       //WebView에서 확대/축소 기능지원 여부 (defalut: true)
            cacheMode = WebSettings.LOAD_DEFAULT    //캐시가 만료된 경우 네트워크 사용  (defalut: LOAD_DEFAULT)

        }

        webUrl?.let { binding.webView.loadUrl(it) }

    }
}