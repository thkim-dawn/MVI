package com.taehoon.mvi.repository.data

import com.taehoon.mvi.domain.entity.GalleryEntity

data class GalleryRepositoryData(
    val id: String,
    val downloadUrl: String
) {
    fun mapToGallery() = GalleryEntity(id, downloadUrl)
}