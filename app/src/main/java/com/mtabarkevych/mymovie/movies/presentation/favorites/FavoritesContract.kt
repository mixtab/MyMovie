package com.mtabarkevych.mymovie.movies.presentation.favorites

import com.mtabarkevych.mymovie.core.presentation.UiEffect
import com.mtabarkevych.mymovie.core.presentation.UiEvent
import com.mtabarkevych.mymovie.core.presentation.UiState
import com.mtabarkevych.mymovie.movies.presentation.HomeTabs


data class FavoritesUiState(
    val showLoading: Boolean
) : UiState {
    val tabs = HomeTabs.entries
}

sealed interface FavoritesUiEvent : UiEvent {
    data object OnMovieClicked : FavoritesUiEvent
}

sealed interface FavoritesUiEffect : UiEffect {
    data class ShowMessage(val message: String) : FavoritesUiEvent
}