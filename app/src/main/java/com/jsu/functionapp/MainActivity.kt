package com.jsu.functionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jsu.functionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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

        adtSetting()

    }

    private fun adtSetting() {

        val arrayAdtName = arrayListOf<String>("Recycler View Sample", "Dialog Sample")

        adtCard = MainCardAdapter(activity,arrayAdtName)
        binding.rvList.adapter = adtCard

    }

}