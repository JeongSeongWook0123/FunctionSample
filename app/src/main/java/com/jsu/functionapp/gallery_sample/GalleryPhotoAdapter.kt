package com.jsu.functionapp.gallery_sample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jsu.functionapp.R
import com.jsu.functionapp.databinding.ListviewGalleryPhotoBinding
import com.jsu.functionapp.databinding.ListviewRecyclerSampleBinding


class GalleryPhotoAdapter(
    private var context: Context,
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

                Glide.with(context)
                     .load(mediaStoreImage.contentUri)
                     .centerCrop()
                     .into(holder.ivPhoto)

            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class PhotoHolder(itemView: ListviewGalleryPhotoBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        var view = itemView.root
        var ivPhoto = itemView.ivPhoto

    }


}

