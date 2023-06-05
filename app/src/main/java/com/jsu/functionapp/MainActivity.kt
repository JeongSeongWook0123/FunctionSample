package com.jsu.functionapp

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.jsu.functionapp.databinding.ActivityMainBinding

class MainActivity : BaseVBActivity() {

    private val activity = this
    private lateinit var binding: ActivityMainBinding
    private var adtCard : MainCardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    //test1
        viewInit()
    }


    private fun viewInit() {

        setTitleBar("Main", null)

        adtSetting()

    }

    private fun adtSetting() {

        val arrayAdtName = arrayListOf<String>("Recycler View Sample", "Dialog Sample",
            "Device Size Measure","Test Sample","Test Sample","Test Sample")
        adtCard = MainCardAdapter(activity,arrayAdtName){
            //Measure Device Size
            measureDeviceSize()

        }
        binding.rvList.adapter = adtCard
        binding.rvList.layoutManager = GridLayoutManager(activity,3)

    }

    private fun measureDeviceSize() {

        //모바일 기기 해상도 dp
        val metricWidth = resources.displayMetrics.widthPixels
        val metricHeight = resources.displayMetrics.heightPixels
        val density = resources.displayMetrics.density

        //resources.getIdentifier를 사용하면 id를 동적으로 받아와 활용 할 수 있다. (name, type, package)
        var statusbarHeight = 0
        val resourceID = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceID > 0)
            statusbarHeight = resources.getDimensionPixelSize(resourceID) //dp나sp 사이즈 값을 int형으로 가져온다.


        val resourceId1 = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var navigationbarHeight = 0
        if (resourceId1 > 0)
            navigationbarHeight = resources.getDimensionPixelSize(resourceId1)

        Toast.makeText(activity,"StatusBar Height:${statusbarHeight} \nNavigationBarHeight:${navigationbarHeight}",Toast.LENGTH_SHORT).show()



    }

}