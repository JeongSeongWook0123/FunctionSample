package com.jsu.functionapp.dialogsample

import android.app.AlertDialog
import android.view.Gravity
import androidx.constraintlayout.widget.ConstraintLayout
import com.jsu.functionapp.R


open class BaseDialogVBHelper() {

    open val builder: AlertDialog.Builder? = null

    open var bottomAnim = false
    open var cancelable: Boolean = true
    open var gravity: Int = Gravity.CENTER
    open var isbackGroundTransparent: Boolean = true
    open var totalSize = false
    open var dialog: AlertDialog? = null


    fun build() {

        dialog = builder?.setCancelable(cancelable)?.create()

        if (isbackGroundTransparent) {
            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        } else {
            dialog?.window?.setBackgroundDrawableResource(android.R.color.black)

        }
        if (bottomAnim) {
            dialog?.window?.attributes?.windowAnimations = R.style.dialogAnim
        }
    }

    open fun show() {
        dialog?.show()
        val windows = dialog?.window
        if (totalSize) {
            windows?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
        } else {
            windows?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        }
        if (gravity != Gravity.CENTER) {
            dialog?.window?.setGravity(gravity)
        }
    }

    open fun dismiss() {

        dialog?.dismiss()

    }



}


