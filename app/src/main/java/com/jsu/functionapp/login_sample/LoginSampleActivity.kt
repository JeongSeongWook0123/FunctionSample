package com.jsu.functionapp.login_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ActivityLoginSampleBinding

class LoginSampleActivity : AppCompatActivity() {
    private val activity = this
    private lateinit var binding: ActivityLoginSampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun viewInit() {

    }
}