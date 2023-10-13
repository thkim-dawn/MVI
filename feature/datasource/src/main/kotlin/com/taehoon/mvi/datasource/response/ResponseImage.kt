package com.taehoon.mvi.datasource.response

import com.google.gson.annotations.SerializedName
import com.taehoon.mvi.repository.data.GalleryRepositoryData

data class ResponseImage(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("download_url")
    val downloadUrl: String? = null
) {
    fun mapToImageRepositoryData() = GalleryRepositoryData(id ?: "", downloadUrl ?: "")
}