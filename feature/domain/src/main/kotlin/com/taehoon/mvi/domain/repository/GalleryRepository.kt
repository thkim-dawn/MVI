package com.taehoon.mvi.domain.repository

import androidx.paging.PagingData
import com.taehoon.mvi.domain.entity.GalleryEntity
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    suspend fun getGalleryPagingData(): Flow<PagingData<GalleryEntity>>
}