package com.taehoon.common.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MviReducer<INTENT : MviIntent, VIEW_STATE : MviViewState, SIDE_EFFECT : MviSideEffect>(
    private val scope: CoroutineScope,
    initialViewState: VIEW_STATE,
    private val handleIntent: suspend (INTENT) -> Unit
) {
    private val _stateFlow = MutableStateFlow(initialViewState)
    val stateFlow = _stateFlow.asStateFlow()

    private val _intentFlow = MutableSharedFlow<INTENT>()
    private val _sideEffectFlow = Channel<SIDE_EFFECT>()
    val sideEffectFlow = _sideEffectFlow.receiveAsFlow()

    init {
        handleEvent()
    }

    private fun handleEvent() {
        _intentFlow.onEach { event ->
            handleIntent(event)
        }.launchIn(scope)
    }

    fun setViewState(reduce: suspend VIEW_STATE.() -> VIEW_STATE) {
        scope.launch {
            _stateFlow.emit(_stateFlow.value.reduce())
        }
    }

    fun setIntent(intent: INTENT) {
        scope.launch {
            _intentFlow.emit(intent)
        }
    }

    fun setSideEffect(sideEffect: SIDE_EFFECT) {
        scope.launch {
            _sideEffectFlow.send(sideEffect)
        }
    }
}