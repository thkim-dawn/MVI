package com.taehoon.mvi.datasource.api

import com.taehoon.mvi.datasource.response.ResponseImage
import com.taehoon.mvi.repository.GalleryRepositoryImpl.Companion.DEFAULT_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApi {
    @GET("/v2/list")
    suspend fun fetchImage(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
    ): List<ResponseImage>
}