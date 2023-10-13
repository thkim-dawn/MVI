package com.taehoon.mvi.presentation

import com.taehoon.common.mvi.MviSideEffect

sealed class GallerySideEffect : MviSideEffect {
    object ShowErrorToast : GallerySideEffect()
    data class ShowLoading(val isShowLoading: Boolean) : GallerySideEffect()
}