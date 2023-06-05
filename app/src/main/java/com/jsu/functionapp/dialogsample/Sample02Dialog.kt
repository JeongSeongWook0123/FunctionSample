package com.jsu.functionapp.dialogsample

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.DialogSample02Binding


class Sample02Dialog(var context: Context) : BaseDialogVBHelper() {

    val dialogView: DialogSample02Binding =
        DialogSample02Binding.inflate(LayoutInflater.from(context), null, false)
    val binding: DialogSample02Binding get() = dialogView

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(binding.root)

    fun create(): Sample02Dialog {
        build()
        dialogViewInit()
        return this
    }

    private fun dialogViewInit() {

        // 1. Shape 파일 가져 오기
        // mutate()를 하지 않는 경우 rounded10_gray 자체 값이 변경 되어 다른 Dialog를 클릭 했을 때 변경된 속성 값이 남아 있음
        val shapeLeft = ContextCompat.getDrawable(context,R.drawable.rounded10_gray)?.mutate() as GradientDrawable
        val shapeRight = ContextCompat.getDrawable(context,R.drawable.rounded10_gray)?.mutate() as GradientDrawable
        val shapeBackground = ContextCompat.getDrawable(context,R.drawable.rounded10_gray)?.mutate() as GradientDrawable

        // 2. 원하는 색상으로 변경
        shapeLeft.setColor(ContextCompat.getColor(context,R.color.gray_cde))
        shapeRight.setColor(ContextCompat.getColor(context,R.color.violet_8fa))
        shapeBackground.setColor(ContextCompat.getColor(context,R.color.white))

        // 3. View에 수정된 Drawable 설정
        binding.btnBlockNo.background = shapeLeft
        binding.btnBlockOk.background = shapeRight
        binding.layContent.background = shapeBackground

        binding.btnBlockNo.setOnClickListener {
            dismiss()
        }

        binding.btnBlockOk.setOnClickListener {
            dismiss()
        }


    }


}
