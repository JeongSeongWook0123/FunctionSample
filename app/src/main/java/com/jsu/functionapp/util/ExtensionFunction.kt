package com.jsu.functionapp.util

import android.view.View
import com.jsu.functionapp.componenets.OnThrottleFirstListener

object ExtensionFunction {

    fun View.onThrottleClick(delayTime: Long, action: (v: View) -> Unit) {
        val listener = View.OnClickListener { action(this) }
        setOnClickListener(OnThrottleFirstListener(listener, delayTime))
    }


}