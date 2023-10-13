package com.taehoon.mvi.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.taehoon.mvi.domain.entity.GalleryEntity
import com.taehoon.mvi.domain.repository.GalleryRepository
import com.taehoon.mvi.repository.datasource.GalleryRemoteDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val galleryRemoteDatasource: GalleryRemoteDatasource,
) : GalleryRepository {
    override suspend fun getGalleryPagingData(): Flow<PagingData<GalleryEntity>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
        ) {
            galleryRemoteDatasource.fetchGalleryPagingData()
        }.flow.map { it.map { imageRepositoryData -> imageRepositoryData.mapToGallery() } }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}