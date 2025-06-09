package com.mtabarkevych.mymovie.movies.presentation.favorites

import androidx.annotation.StringRes
import com.mtabarkevych.mymovie.core.presentation.UiEffect
import com.mtabarkevych.mymovie.core.presentation.UiEvent
import com.mtabarkevych.mymovie.core.presentation.UiState
import com.mtabarkevych.mymovie.movies.domain.model.Movie

data class FavoritesUiState(
    val favoriteMovies: List<Movie> = listOf()
) : UiState

sealed interface FavoritesUiEvent : UiEvent {
    data class OnUnlikeClicked(val movie: Movie) : FavoritesUiEvent
    data class OnShareClicked(val movie: Movie) : FavoritesUiEvent
}

sealed interface FavoritesUiEffect : UiEffect {
    data class ShowShareMovie(val movie: Movie) : FavoritesUiEffect
    data class ShowMessage(@StringRes val stringRes: Int) : FavoritesUiEffect
}