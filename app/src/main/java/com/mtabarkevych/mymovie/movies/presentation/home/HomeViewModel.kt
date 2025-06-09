package com.mtabarkevych.mymovie.movies.presentation.home

import com.mtabarkevych.mymovie.core.presentation.MVIViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel() : MVIViewModel<HomeUiState, HomeUiEvent, HomeUiEffect>() {

    private val _uiState = MutableStateFlow(HomeUiState())
    override val uiState = _uiState.asStateFlow()


    override fun processUiEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnTabClicked -> Unit
        }
    }
}