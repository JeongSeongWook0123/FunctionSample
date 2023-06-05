package com.jsu.functionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.jsu.functionapp.databinding.ActivityMainBinding
import com.jsu.functionapp.recyclersample.RecyclerViewSample01Activit

class MainActivity : BaseVBActivity() {

    private val activity = this
    private lateinit var binding: ActivityMainBinding
    private var adtCard : MainCardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()
    }


    private fun viewInit() {

        setTitleBar("Main", null)

        adtSetting()

    }

    private fun adtSetting() {

        val arrayAdtName = arrayListOf<String>("Recycler View Sample", "Dialog Sample",
            "Test Sample01","Test Sample02","Test Sample03","Test Sample04")
        adtCard = MainCardAdapter(activity,arrayAdtName)
        binding.rvList.adapter = adtCard
        binding.rvList.layoutManager = GridLayoutManager(activity,3)

    }

}