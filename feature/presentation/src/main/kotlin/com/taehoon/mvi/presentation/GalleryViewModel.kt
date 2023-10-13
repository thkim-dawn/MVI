package com.taehoon.mvi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taehoon.common.mvi.Container
import com.taehoon.common.mvi.MviReducer
import com.taehoon.mvi.domain.usecase.GalleryUseCase
import com.taehoon.mvi.presentation.data.Mapper.mapToGalleryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryUseCase: GalleryUseCase
) : ViewModel(), Container<GalleryIntent, GalleryViewState, GallerySideEffect> {

    override val reducer: MviReducer<GalleryIntent, GalleryViewState, GallerySideEffect> =
        MviReducer(
            scope = viewModelScope,
            initialViewState = GalleryViewState(),
            handleIntent = ::handleIntent
        )

    override val viewStateFlow: StateFlow<GalleryViewState> = reducer.stateFlow
    override val sideEffectFlow: Flow<GallerySideEffect> = reducer.sideEffectFlow
    override fun dispatcherIntent(intent: GalleryIntent) {
        reducer.setIntent(intent)
    }

    override suspend fun handleIntent(intent: GalleryIntent) {
        when (intent) {
            GalleryIntentOnStart -> onStart()
        }
    }


    private suspend fun onStart() {
        galleryUseCase.getGalleryPagingData()
            .cachedIn(viewModelScope)
            .onStart { reducer.setSideEffect(GallerySideEffect.ShowLoading(true)) }
            .map { pagingData -> pagingData.map { it.mapToGalleryItem() } }
            .catch {
                reducer.setSideEffect(GallerySideEffect.ShowErrorToast)
            }
            .collect {
                reducer.setViewState {
                    this.copy(stateType = GalleryViewStateType.LoadedGalleryList, galleryPagingData = it)
                }
                reducer.setSideEffect(GallerySideEffect.ShowLoading(false))
            }
    }

}