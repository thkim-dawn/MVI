package com.taehoon.mvi.main.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.taehoon.mvi.main.databinding.ItemGalleryBinding
import com.taehoon.mvi.presentation.data.GalleryItem

class GalleryRecyclerAdapter : PagingDataAdapter<GalleryItem, GalleryViewHolder>(FavoriteItemDataDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object FavoriteItemDataDiffUtil : DiffUtil.ItemCallback<GalleryItem>() {

    override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem == newItem
    }
}