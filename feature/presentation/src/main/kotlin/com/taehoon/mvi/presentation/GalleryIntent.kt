package com.taehoon.mvi.presentation

import com.taehoon.common.mvi.MviIntent

sealed interface GalleryIntent : MviIntent

object GalleryIntentOnStart : GalleryIntent