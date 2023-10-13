package com.taehoon.mvi.datasource.datasource

import androidx.paging.PagingSource
import com.taehoon.mvi.datasource.api.PicsumApi
import com.taehoon.mvi.datasource.paging.GalleryPagingSource
import com.taehoon.mvi.repository.data.GalleryRepositoryData
import com.taehoon.mvi.repository.datasource.GalleryRemoteDatasource
import javax.inject.Inject

class GalleryRemoteDatasourceImpl @Inject constructor(private val picsumApi: PicsumApi) : GalleryRemoteDatasource {
    override fun fetchGalleryPagingData(): PagingSource<Int, GalleryRepositoryData> {
        return GalleryPagingSource(picsumApi)
    }
}