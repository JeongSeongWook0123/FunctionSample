package com.jsu.functionapp.componenets

import android.view.View


class OnThrottleFirstListener(private val clickListener: View.OnClickListener,private val time: Long) : View.OnClickListener {

    private var clickable = true

    override fun onClick(v: View?) {
        if (clickable) {
            clickable = false
            v?.run {
                postDelayed({ clickable = true }, time)
                clickListener.onClick(v)
            }
        }
    }
}