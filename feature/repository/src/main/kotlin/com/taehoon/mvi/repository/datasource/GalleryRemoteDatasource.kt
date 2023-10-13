package com.taehoon.mvi.repository.datasource

import androidx.paging.PagingSource
import com.taehoon.mvi.repository.data.GalleryRepositoryData

interface GalleryRemoteDatasource {
    fun fetchGalleryPagingData(): PagingSource<Int, GalleryRepositoryData>
}