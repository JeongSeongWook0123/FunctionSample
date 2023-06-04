package com.jsu.functionapp.dialogSample

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ListviewVpImageBinding

class DialogViewPagerAdapter(val context: Activity, private val imageArray: ArrayList<Int>):
RecyclerView.Adapter<DialogViewPagerAdapter.ViewPagerHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {

        val view = ListviewVpImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewPagerHolder(view)

    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {

        holder.bindWithView(imageArray[position])

    }

    override fun getItemCount(): Int = imageArray.size

    inner class ViewPagerHolder(itemView: ListviewVpImageBinding) : RecyclerView.ViewHolder(itemView.root) {

        private val binding = itemView

        fun bindWithView(image: Int) {

            binding.ivSample.setBackgroundResource(image)
        }

    }

}