package com.jsu.functionapp.dialogSample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ActivityDialogSample01Binding

class DialogSample01Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDialogSample01Binding
    private var activity = this

    private var dialogSample01: Sample01Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogSample01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()

    }

    private fun viewInit() {

        dialogSample01 = Sample01Dialog(activity)
        binding.btnSample.setOnClickListener {
            dialogSample01?.create()
            dialogSample01?.show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if(dialogSample01 != null) {
            dialogSample01?.dismiss()
        }
    }
}