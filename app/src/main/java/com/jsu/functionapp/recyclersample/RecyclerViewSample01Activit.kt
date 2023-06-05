package com.jsu.functionapp.recyclersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jsu.functionapp.BaseVBActivity
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ActivityRecyclerViewSampleBinding

class RecyclerViewSample01Activit : BaseVBActivity() {
    private val activity = this
    private lateinit var binding: ActivityRecyclerViewSampleBinding
    private var adtSample : RecyclerViewSample01Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()

    }


    private fun viewInit() {
        setTitleBar("RecyclerView Sample Page", View.OnClickListener { onBackPressed()})
        adtSetting()
    }

    private fun adtSetting() {

        adtSample = RecyclerViewSample01Adapter(activity)
        binding.recyclerView.adapter = adtSample

        /**
         * RecyclerView의 전체 크기가 고정된 경우에 사용한다.(Item의 수와 관계없이)
         * true -> RecyclerView는 Item의 크기나 수와 상관 없이 고정된 크기를 갖음
         * false -> 고정된 크기를 갖지 않음
         * ------------------------------------------------------------------------------------
         * RecyclerView는 아이템의 크기나 수가 변경되는 경우 RecyclerView의 전체 크기를 재측정 하게 되는데
         * setHasFixedSize(true)로 설정하는 경우 크기가 고정되기 때문에 전체크기를 재측정하는 과정을 하지않아
         * 더 효율적으로 동작하게 된다.
         * */
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)




    }


}