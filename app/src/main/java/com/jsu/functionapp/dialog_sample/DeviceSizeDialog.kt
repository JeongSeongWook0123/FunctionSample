package com.jsu.functionapp.dialog_sample

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.DialogDeviceSizeBinding


class DeviceSizeDialog(var context: Context,var arrayValue: ArrayList<String>) : BaseDialogVBHelper() {

    val dialogView: DialogDeviceSizeBinding =
        DialogDeviceSizeBinding.inflate(LayoutInflater.from(context), null, false)
    val binding: DialogDeviceSizeBinding get() = dialogView

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(binding.root)

    fun create(): DeviceSizeDialog {
        build()
        dialogViewInit()
        return this
    }

    private fun dialogViewInit() {

        /**
         * Drawable객체는 기본적으로 공유 상태를 유지하기 때문에 동일한 Drawable객체를 여러곳에서 사용하면
         * 한곳에서 변경된 상태가 다른 곳에도 영향을 미치기 때문에 mutate()함수를 사용해서 사용해야 한다.
         * mutate() -> Drawable 객체의 복사본을 생성
         * */
        // 1. Shape 파일 가져 오기
        // mutate()를 하지 않는 경우 rounded10_gray 자체 값이 변경 되어 다른 Dialog를 클릭 했을 때 변경된 속성 값이 남아 있음

        val shapeCenter = ContextCompat.getDrawable(context, R.drawable.rounded10_gray)
            ?.mutate() as GradientDrawable
        val shapeBackground = ContextCompat.getDrawable(context, R.drawable.rounded10_gray)
            ?.mutate() as GradientDrawable


        shapeCenter.setColor(ContextCompat.getColor(context, R.color.violet_8fa))
        shapeBackground.setColor(ContextCompat.getColor(context, R.color.white))

        // 4. View에 수정된 Drawable 설정
        binding.btnBlockOk.background = shapeCenter
        binding.layContent.background = shapeBackground


        val arrayTv = arrayListOf(
            binding.tvWidthPxValue, binding.tvHeightPxValue,
            binding.tvWidthDpValue, binding.tvHeightDpValue,
            binding.tvDensityValue, binding.tvStatusHeightValue, binding.tvNavigationHeightValue
        )

        for(i in arrayTv.indices) {
            arrayTv[i].text = arrayValue[i]
        }

        binding.btnBlockOk.setOnClickListener {
            dismiss()
        }


    }

    //dp를 px로 변환
    fun Int.dpToPx(): Float {
        return this * context.resources.displayMetrics.density
    }

}
