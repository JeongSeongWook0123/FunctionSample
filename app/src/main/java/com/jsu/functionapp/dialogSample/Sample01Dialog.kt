package com.jsu.functionapp.dialogSample

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.DialogSample01Binding
import org.json.JSONArray

/*target : 댓글인지 게시글인지 구분
  writerCustIdx : 게시글 작성자 CustIdx
  writer : 작성자 닉네임
  profileImage : 작성자 프로필 이미지
  postWriterIdx : 게시글 Idx
 */
class Sample01Dialog(val context: Context) : BaseDialogVBHelper() {

    val dialogView: DialogSample01Binding =
        DialogSample01Binding.inflate(LayoutInflater.from(context), null, false)
    val binding: DialogSample01Binding get() = dialogView

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(binding.root)

    fun create(): Sample01Dialog {
        build()

        // 1. Shape 파일 가져오기
        val shape = ContextCompat.getDrawable(context,R.drawable.rounded10_blue) as GradientDrawable

        // 2. 원하는 radius 값으로 변경
        shape.cornerRadii = floatArrayOf(0f,0f,0f,0f,30f,30f,0f,0f)

        // 3. 수정된 Shape 파일을 다시 Drawable로 설정
        val newDrawable = shape

        // 4. View에 수정된 Drawable 설정
        binding.btnBlockOk.background = newDrawable

        binding.btnBlockNo.setOnClickListener {
            dismiss()
        }

        binding.btnBlockOk.setOnClickListener {
            dismiss()
        }

        return this
    }


}
