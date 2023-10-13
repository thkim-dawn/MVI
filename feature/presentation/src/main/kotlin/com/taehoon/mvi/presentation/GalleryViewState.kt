package com.taehoon.mvi.presentation

import androidx.paging.PagingData
import com.taehoon.common.mvi.MviViewState
import com.taehoon.mvi.presentation.data.GalleryItem

data class GalleryViewState(
    val stateType: GalleryViewStateType = GalleryViewStateType.Initial,
    val galleryPagingData: PagingData<GalleryItem>? = null,
    val showLoading: Boolean = false,
) : MviViewState

sealed interface GalleryViewStateType {
    object Initial : GalleryViewStateType
    object LoadedGalleryList : GalleryViewStateType
}