package com.jsu.functionapp.gallery_sample

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ListviewGalleryPhotoBinding
import com.jsu.functionapp.databinding.ListviewRecyclerSampleBinding


class GalleryPhotoAdapter(
    private var context: Context,
    private var selectPosition: Int? = -1,
    private var viewHandler: (imageUrl: Uri) -> Unit
) : ListAdapter<MediaStoreImage, RecyclerView.ViewHolder>(MediaStoreImage.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val listBinding =
            ListviewGalleryPhotoBinding.inflate(LayoutInflater.from(context), parent, false)

        return PhotoHolder(listBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val mediaStoreImage = getItem(position)

        when (holder) {
            is PhotoHolder -> {

                if(selectPosition != position) {
                    holder.ivCheck.visibility = View.GONE
                }

                Glide.with(context)
                     .load(mediaStoreImage.contentUri)
                     .centerCrop()
                     .into(holder.ivPhoto)

                holder.view.setOnClickListener {
                    checkBtn(position)
                    viewHandler.invoke(mediaStoreImage.contentUri)
                    holder.view.setBackgroundResource(R.color.gray_cde)
                    holder.ivCheck.visibility = View.VISIBLE
                }

            }
        }

    }

    override fun getItemViewType(position: Int): Int = position
    fun checkBtn(recordPosition: Int) {
        selectPosition = recordPosition
        notifyDataSetChanged()
    }

    inner class PhotoHolder(itemView: ListviewGalleryPhotoBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        var view = itemView.root
        var ivPhoto = itemView.ivPhoto
        var ivCheck = itemView.ivCheck

    }


}

