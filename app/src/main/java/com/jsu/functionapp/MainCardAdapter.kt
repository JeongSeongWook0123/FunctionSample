package com.jsu.functionapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jsu.functionapp.databinding.ListviewCardViewMainBinding
import com.jsu.functionapp.databinding.ListviewRecyclerSampleBinding


class MainCardAdapter(
    private var context: Context,
    private var arrayCardName: ArrayList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val listBinding = ListviewCardViewMainBinding.inflate(LayoutInflater.from(context),parent,false)

        return SampleListHolder(listBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is SampleListHolder -> {

                holder.tvName.text = arrayCardName[position]

            }
        }

    }

    override fun getItemCount(): Int = arrayCardName.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class SampleListHolder(itemView: ListviewCardViewMainBinding) : RecyclerView.ViewHolder(itemView.root) {

        var view = itemView.root
        var tvName = itemView.tvCardName

    }


}

