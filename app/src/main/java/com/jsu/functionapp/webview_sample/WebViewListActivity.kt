package com.jsu.functionapp.webview_sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jsu.functionapp.BaseVBActivity
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ActivityWebViewListBinding

class WebViewListActivity : BaseVBActivity() {
    private val activity = this
    private lateinit var binding: ActivityWebViewListBinding
    private var adtWebview: WebViewListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()

    }

    private fun viewInit() {

        setTitleBar("WebView List", View.OnClickListener { onBackPressed() })
        adtSetting()

    }

    private fun adtSetting() {

        val arrayUrl: ArrayList<String> = arrayListOf("GitHub", "Naver", "Daum", "Nate")
        var arrayPutUrl: String? = null
        adtWebview = WebViewListAdapter(activity, arrayUrl) {

            when(it) {
                "GitHub" -> {arrayPutUrl = resources.getString(R.string.github)}
                "Naver" -> {arrayPutUrl = resources.getString(R.string.naver)}
                "Daum" -> {arrayPutUrl = resources.getString(R.string.daum)}
                "Nate" -> {arrayPutUrl = resources.getString(R.string.nate)}
            }

            val intent = Intent(activity, WebViewSample01Activity::class.java)
            intent.putExtra("WebUrl", arrayPutUrl)
            startActivity(intent)

        }

        binding.rvList.adapter = adtWebview
        binding.rvList.layoutManager = LinearLayoutManager(activity)



    }
}