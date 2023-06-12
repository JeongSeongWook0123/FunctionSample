package com.jsu.functionapp

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


open class BaseVBActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setTitleBar(
        titleName: String,
        btn1: View.OnClickListener?,
        btn2: View.OnClickListener? = null
    ) {

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

    fun getKeyHash() {

        try {

           val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {  //API33
               packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
           } else {
               packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
           }

            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("tjddnr", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }


}