package com.mtabarkevych.mymovie.core.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface UiState
interface UiEvent
interface UiEffect

abstract class MVIViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel() {

    abstract val uiState: StateFlow<State>
    protected val currentState get() = uiState.value

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect = _effect.asSharedFlow()

    fun sendUiEvent(event: Event) {
        Log.i(TAG_EVENT, event.toString())
        processUiEvent(event)
    }

    protected abstract fun processUiEvent(event: Event)

    protected fun setUiEffect(builder: suspend () -> Effect) {
        viewModelScope.launch {
            val effectValue = builder()
            Log.i(TAG_EFFECT, effectValue.toString())
            _effect.emit(effectValue)
        }
    }

    companion object {
        const val TAG_STATE = "ScreenUiState"
        private const val TAG_EVENT = "ScreenUiEvent"
        private const val TAG_EFFECT = "ScreenUiEffect"
    }
}