package com.jsu.functionapp.dialog_sample

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jsu.functionapp.BaseVBActivity
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ActivityDialogSample01Binding
import com.jsu.functionapp.databinding.DialogBottomSheetSample01Binding
import com.jsu.functionapp.databinding.DialogBottomSheetSample02Binding

class DialogSample01Activity : BaseVBActivity() {
    private lateinit var binding: ActivityDialogSample01Binding
    private var activity = this

    private var dialogSample01: Sample01Dialog? = null
    private var dialogSample02: Sample02Dialog? = null
    private var dialogSample03: BottomSheetDialog? = null
    private lateinit var bottomSheet01Binding: DialogBottomSheetSample01Binding

    private var dialogSample04: BottomSheetDialog? = null
    private lateinit var bottomSheet02Binding: DialogBottomSheetSample02Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogSample01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()

    }

    private fun viewInit() {

        setTitleBar("Dialog Sample Page", View.OnClickListener { onBackPressed() })

        val arrayBtnSample = arrayListOf(
            binding.btnSample1, binding.btnSample2,
            binding.btnSample3, binding.btnSample4
        )

        for (i in arrayBtnSample.indices) {
            arrayBtnSample[i].text = activity.getString(R.string.custom_dialog) + (i + 1)
        }


        binding.btnSample1.setOnClickListener {

            dialogSample01 = Sample01Dialog(activity)
            dialogSample01?.create()
            dialogSample01?.show()

        }

        binding.btnSample2.setOnClickListener {

            dialogSample02 = Sample02Dialog(activity)
            dialogSample02?.create()
            dialogSample02?.show()

        }

        binding.btnSample3.setOnClickListener {
            bottomSheet01Setting()
        }

        binding.btnSample4.setOnClickListener {
            bottomSheet02Setting()
        }

    }

    private fun bottomSheet01Setting() {

        dialogSample03 = BottomSheetDialog(activity)
        val newSheetLayout = layoutInflater.inflate(R.layout.dialog_bottom_sheet_sample_01,null)
        bottomSheet01Binding =
            DialogBottomSheetSample01Binding.inflate(layoutInflater, newSheetLayout as ViewGroup, false)

        dialogSample03?.let { popup ->
            popup.behavior.state = BottomSheetBehavior.STATE_EXPANDED   //BottomSheet가 전체 화면을 차지하도록 확장
            popup.behavior.skipCollapsed = true                         //축소된 상태를 건너뛰고 확장된 상태로 전환
            popup.behavior.isHideable = true                            //BottomSheet를 숨기는 속성
            popup.setContentView(bottomSheet01Binding.root)
            popup.show()
        }

        bottomSheet01Binding.btnConfirm.setOnClickListener {

            dialogSample03?.dismiss()
        }

    }

    private fun bottomSheet02Setting() {

        dialogSample04 = BottomSheetDialog(activity)
        val newSheetLayout = layoutInflater.inflate(R.layout.dialog_bottom_sheet_sample_02,null)
        bottomSheet02Binding =
            DialogBottomSheetSample02Binding.inflate(layoutInflater, newSheetLayout as ViewGroup, false)
        val arrayBitmap : ArrayList<Int> = arrayListOf()

        for (i in 0..4) {
           arrayBitmap.add(resources.getIdentifier("image_sample0${i+1}","drawable",activity.packageName))
        }

        bottomSheet02Binding.vpSample.adapter = DialogViewPagerAdapter(activity,arrayBitmap)

        dialogSample04?.let { popup ->
            popup.behavior.state = BottomSheetBehavior.STATE_EXPANDED   //BottomSheet가 전체 화면을 차지하도록 확장
            popup.behavior.skipCollapsed = true                         //축소된 상태를 건너뛰고 확장된 상태로 전환
            popup.behavior.isHideable = true                            //BottomSheet를 숨기는 속성
            popup.setContentView(bottomSheet02Binding.root)
            popup.show()
        }

        bottomSheet02Binding.btnConfirm.setOnClickListener {

            dialogSample04?.dismiss()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialogSample01 != null) {
            dialogSample01?.dismiss()
        }
    }
}