package com.taehoon.mvi.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taehoon.mvi.datasource.api.PicsumApi
import com.taehoon.mvi.repository.data.GalleryRepositoryData

class GalleryPagingSource(private val picsumApi: PicsumApi, private val pageSize: Int = 20) : PagingSource<Int, GalleryRepositoryData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryRepositoryData> {
        return try {
            val nextPage = params.key ?: 1
            val responseImageList = picsumApi.fetchImage(page = nextPage, limit = pageSize)
            LoadResult.Page(
                data = responseImageList.map { it.mapToImageRepositoryData() },
                prevKey = null,
                nextKey = if (responseImageList.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryRepositoryData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}