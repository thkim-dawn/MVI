package com.taehoon.mvi.domain.usecase

import androidx.paging.PagingData
import com.taehoon.mvi.domain.entity.GalleryEntity
import com.taehoon.mvi.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow

class GalleryUseCase(private val galleryRepository: GalleryRepository) {
    suspend fun getGalleryPagingData(): Flow<PagingData<GalleryEntity>> = galleryRepository.getGalleryPagingData()
}