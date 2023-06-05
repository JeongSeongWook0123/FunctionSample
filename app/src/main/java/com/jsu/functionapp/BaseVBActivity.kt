package com.jsu.functionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

open class BaseVBActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setTitleBar(titleName: String, btn1: View.OnClickListener?, btn2: View.OnClickListener? = null) {

        val tvName = findViewById<TextView>(R.id.tv_title)
        val btnLeft = findViewById<ImageButton>(R.id.btn_left)
        val btnRight = findViewById<ImageButton>(R.id.btn_right)

        tvName.text = titleName

        btn1?.let {
            btnLeft.visibility = View.VISIBLE
            btnLeft.setOnClickListener(it)
        }

        btn2?.let {
            btnRight.visibility = View.VISIBLE
            btnRight.setOnClickListener(it)
        }





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