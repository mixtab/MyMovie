package com.mtabarkevych.mymovie.movies.presentation.favorites

import androidx.lifecycle.viewModelScope
import com.mtabarkevych.mymovie.R
import com.mtabarkevych.mymovie.core.presentation.MVIViewModel
import com.mtabarkevych.mymovie.movies.data.MoviesRepository
import com.mtabarkevych.mymovie.movies.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class FavoritesViewModel(
    private val moviesRepository: IMoviesRepository
) : MVIViewModel<FavoritesUiState, FavoritesUiEvent, FavoritesUiEffect>() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    override val uiState = _uiState.combine(moviesRepository.getFavorites()) { state, favorites ->
        state.copy(favoriteMovies = favorites)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), FavoritesUiState())


    override fun processUiEvent(event: FavoritesUiEvent) {
        when (event) {
            is FavoritesUiEvent.OnUnlikeClicked -> {
                viewModelScope.launch {
                    moviesRepository.setMovie(event.movie.copy(isFavorite = false))
                    setUiEffect { FavoritesUiEffect.ShowMessage(R.string.movie_removed) }
                }
            }

            is FavoritesUiEvent.OnShareClicked -> setUiEffect {
                FavoritesUiEffect.ShowShareMovie(event.movie)
            }
        }
    }

}