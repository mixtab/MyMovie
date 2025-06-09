package com.mtabarkevych.mymovie.movies.presentation.all_movies

import androidx.annotation.StringRes
import com.mtabarkevych.mymovie.core.presentation.UiEffect
import com.mtabarkevych.mymovie.core.presentation.UiEvent
import com.mtabarkevych.mymovie.core.presentation.UiState
import com.mtabarkevych.mymovie.movies.domain.model.Movie


data class AllMoviesUiState(
    val isRefreshing:Boolean = false,
    val showLoading: Boolean = true
) : UiState

sealed interface AllMoviesUiEvent : UiEvent {
    data class OnLikeClicked(val movie: Movie) : AllMoviesUiEvent
    data class OnShareClicked(val movie: Movie) : AllMoviesUiEvent
}

sealed interface AllMoviesUiEffect : UiEffect {
    data class ShowShareMovie(val movie: Movie) : AllMoviesUiEffect
    data class ShowMessage(@StringRes val stringRes: Int) : AllMoviesUiEffect
}