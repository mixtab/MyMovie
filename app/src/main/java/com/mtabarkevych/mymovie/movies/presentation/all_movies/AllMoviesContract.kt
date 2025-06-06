package com.mtabarkevych.mymovie.movies.presentation.all_movies

import com.mtabarkevych.mymovie.core.presentation.UiEffect
import com.mtabarkevych.mymovie.core.presentation.UiEvent
import com.mtabarkevych.mymovie.core.presentation.UiState
import com.mtabarkevych.mymovie.movies.presentation.HomeTabs


data class AllMoviesUiState(
    val showLoading: Boolean
) : UiState {
    val tabs = HomeTabs.entries
}

sealed interface AllMoviesUiEvent : UiEvent {
    data object OnMovieClicked : AllMoviesUiEvent
}

sealed interface AllMoviesUiEffect : UiEffect {
    data class ShowMessage(val message: String) : AllMoviesUiEffect
}