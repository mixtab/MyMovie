package com.mtabarkevych.mymovie.movies.presentation

import com.mtabarkevych.mymovie.core.presentation.UiEffect
import com.mtabarkevych.mymovie.core.presentation.UiEvent
import com.mtabarkevych.mymovie.core.presentation.UiState

data class HomeUiState(
    val showLoading: Boolean = false,
    val selectedTabIndex:Int = 0
) : UiState {
    val tabs = HomeTabs.entries
}

sealed interface HomeUiEvent : UiEvent {
    data class OnTabClicked(val tab: HomeTabs) : HomeUiEvent
}

sealed interface HomeUiEffect : UiEffect {
    data class ShowMessage(val message: String) : HomeUiEffect
}