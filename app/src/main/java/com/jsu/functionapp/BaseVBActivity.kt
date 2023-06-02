package com.jsu.functionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BaseVBActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //px을 dp로 변환
    fun Float.pxToDp(): Int {
        return (this / resources.displayMetrics.density).toInt()
    }

    //dp를 px로 변환
    fun Int.dpToPx(): Float {
        return this * resources.displayMetrics.density
    }



}