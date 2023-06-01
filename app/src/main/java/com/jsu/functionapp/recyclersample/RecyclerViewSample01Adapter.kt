package com.jsu.functionapp.recyclersample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ListviewRecyclerSampleBinding


class RecyclerViewSample01Adapter(
    private var context: Context,
    private var selectPosition: Int? = -1
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val listBinding = ListviewRecyclerSampleBinding.inflate(LayoutInflater.from(context),parent,false)

        return SampleListHolder(listBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is SampleListHolder -> {

                holder.btnSample.text = context.getString(R.string.sample)+position


                if(selectPosition != position) {
                    holder.btnSample.isSelected = false
                }

                holder.btnSample.setOnClickListener {
                    holder.btnSample.isSelected = true
                    checkBtn(position)
                }

            }
        }

    }

    fun checkBtn(recordPosition: Int) {
        selectPosition = recordPosition
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = 20

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class SampleListHolder(itemView: ListviewRecyclerSampleBinding) : RecyclerView.ViewHolder(itemView.root) {

        var view = itemView.root
        var btnSample = itemView.btnSample

    }


}

