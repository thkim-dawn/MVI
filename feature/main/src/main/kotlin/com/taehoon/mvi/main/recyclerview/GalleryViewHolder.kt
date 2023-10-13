package com.taehoon.mvi.main.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taehoon.mvi.main.databinding.ItemGalleryBinding
import com.taehoon.mvi.presentation.data.GalleryItem

class GalleryViewHolder(
    private val binding: ItemGalleryBinding
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(galleryItem: GalleryItem?) {
        galleryItem?.let {
            Glide.with(binding.root.context)
                .load(it.downloadUrl)
                .centerCrop()
                .error(com.taehoon.common.R.drawable.ic_error)
                .into(binding.thumbnailImageVIew)
        }
    }
}