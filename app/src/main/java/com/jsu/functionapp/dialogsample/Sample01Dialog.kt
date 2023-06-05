package com.jsu.functionapp.dialogsample

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.DialogSample01Binding


class Sample01Dialog(var context: Context) : BaseDialogVBHelper() {

    val dialogView: DialogSample01Binding =
        DialogSample01Binding.inflate(LayoutInflater.from(context), null, false)
    val binding: DialogSample01Binding get() = dialogView

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(binding.root)

    fun create(): Sample01Dialog {
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
        val shapeLeft = ContextCompat.getDrawable(context,R.drawable.rounded10_gray)?.mutate() as GradientDrawable
        val shapeRight = ContextCompat.getDrawable(context,R.drawable.rounded10_gray)?.mutate() as GradientDrawable
        val shapeBackground = ContextCompat.getDrawable(context,R.drawable.rounded10_gray)?.mutate() as GradientDrawable

        // 2. 원하는 radius 값으로 변경
        /**
         * index 2개가 한 세트로 두값 모두 변경 해야 라운드가 적용됨
         * 0,1: 왼쪽 상단 모서리
         * 2,3 : 오른쪽 상단 모서리
         * 4,5 : 오른쪽 하단 모서리
         * 6,7 : 왼쪽 하단 모서리
         * */
        shapeLeft.cornerRadii  = floatArrayOf(0f,0f,0f,0f,0f,0f,10.dpToPx(),10.dpToPx())    //왼쪽 하단 모서리만 라운드처리
        shapeLeft.setColor(ContextCompat.getColor(context,R.color.gray_cde))

        shapeRight.cornerRadii = floatArrayOf(0f,0f,0f,0f,10.dpToPx(),10.dpToPx(),0f,0f)    //오른쪽 하단 모서리만 라운드처리
        shapeRight.setColor(ContextCompat.getColor(context,R.color.violet_8fa))

        shapeBackground.setColor(ContextCompat.getColor(context,R.color.white))

        // 4. View에 수정된 Drawable 설정
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

    //dp를 px로 변환
    fun Int.dpToPx(): Float {
        return this * context.resources.displayMetrics.density
    }
}
