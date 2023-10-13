package com.taehoon.mvi.presentation.data

import com.taehoon.mvi.domain.entity.GalleryEntity

object Mapper {
    fun GalleryEntity.mapToGalleryItem() = GalleryItem(this.id, this.downloadUrl)
}