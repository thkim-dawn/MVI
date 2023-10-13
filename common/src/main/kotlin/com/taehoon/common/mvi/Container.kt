package com.taehoon.common.mvi

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface Container<INTENT : MviIntent, VIEW_STATE : MviViewState, SIDE_EFFECT : MviSideEffect> {
    val reducer: MviReducer<INTENT, VIEW_STATE, SIDE_EFFECT>
    val viewStateFlow: StateFlow<VIEW_STATE>
    val sideEffectFlow: Flow<SIDE_EFFECT>

    suspend fun handleIntent(intent: INTENT)
    fun dispatcherIntent(intent: INTENT)
}

interface MviSideEffect
interface MviViewState
interface MviIntent

fun <INTENT : MviIntent, VIEW_STATE : MviViewState, SIDE_EFFECT : MviSideEffect> Container<INTENT, VIEW_STATE, SIDE_EFFECT>.observe(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    viewState: (suspend (viewState: VIEW_STATE) -> Unit)? = null,
    sideEffect: (suspend (viewState: SIDE_EFFECT) -> Unit)? = null,
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            viewState?.let { launch { viewStateFlow.collect { viewState(it) } } }
            sideEffect?.let { launch { sideEffectFlow.collect { sideEffect(it) } } }
        }
    }
}