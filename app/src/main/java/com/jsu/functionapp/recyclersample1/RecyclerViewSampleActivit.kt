package com.jsu.functionapp.recyclersample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ActivityRecyclerViewSampleBinding

class RecyclerViewSampleActivit : AppCompatActivity() {
    private val activity = this
    private lateinit var binding: ActivityRecyclerViewSampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()

    }


    private fun viewInit() {

        binding.includeTitle.tvTitle.text = activity.getString(R.string.recycler1_title_name)

    }
}