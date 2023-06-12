package com.jsu.functionapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.jsu.functionapp.databinding.ActivityMainBinding
import com.jsu.functionapp.dialog_sample.DeviceSizeDialog

class MainActivity : BaseVBActivity() {

    private val activity = this
    private lateinit var binding: ActivityMainBinding
    private var adtCard: MainCardAdapter? = null
    private var dialogDeviceSize: DeviceSizeDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //test1
        viewInit()
        getKeyHash()
    }


    private fun viewInit() {

        setTitleBar("Main", null)

        adtSetting()

    }

    private fun adtSetting() {

        val arrayAdtName = arrayListOf<String>(
            "Recycler View Sample", "Dialog Sample",
            "Device Size Measure", "WebView Sample",
            "Gallery Sample", "Login Sample",
            "Test Sample"
        )

        adtCard = MainCardAdapter(activity, arrayAdtName) {
            //Measure Device Size
            measureDeviceSize()
        }

        binding.rvList.adapter = adtCard
        binding.rvList.layoutManager = GridLayoutManager(activity, 3)

    }

    private fun measureDeviceSize() {

        //모바일 기기 해상도 dp
        val metricWidth = resources.displayMetrics.widthPixels
        val metricHeight = resources.displayMetrics.heightPixels
        val density = resources.displayMetrics.density

        //resources.getIdentifier를 사용하면 id를 동적으로 받아와 활용 할 수 있다. (name, type, package)
        var statusbarHeight = 0

        val statusBarID = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (statusBarID > 0)
            statusbarHeight =
                resources.getDimensionPixelSize(statusBarID) //dp나sp 사이즈 값을 int형으로 가져온다.


        val navigationId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var navigationBarHeight = 0
        if (navigationId > 0)
            navigationBarHeight = resources.getDimensionPixelSize(navigationId)


        val arrayValue = arrayListOf(
            "${metricWidth}px", "${metricHeight}px",
            "${(metricWidth / density).toInt()}dp", "${(metricHeight / density).toInt()}dp",
            "${density}", "${statusbarHeight}px", "${navigationBarHeight}px"
        )
        deviceSizeDialog(arrayValue)

    }

    private fun deviceSizeDialog(arrValue: ArrayList<String>) {

        dialogDeviceSize = DeviceSizeDialog(activity, arrValue)
        dialogDeviceSize?.create()
        dialogDeviceSize?.show()

    }

}