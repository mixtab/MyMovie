package com.mtabarkevych.mymovie.movies.presentation.all_movies

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mtabarkevych.mymovie.R
import com.mtabarkevych.mymovie.core.presentation.MVIViewModel
import com.mtabarkevych.mymovie.movies.data.MoviesRepository
import com.mtabarkevych.mymovie.movies.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AllMoviesViewModel(
    private val moviesRepository: MoviesRepository
) : MVIViewModel<AllMoviesUiState, AllMoviesUiEvent, AllMoviesUiEffect>() {

    val movies = moviesRepository.getAllMovies()
        .cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow(AllMoviesUiState())
    override val uiState = _uiState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AllMoviesUiState())


    override fun processUiEvent(event: AllMoviesUiEvent) {
        when (event) {
            is AllMoviesUiEvent.OnLikeClicked -> handleLikeClicked(event.movie)
            is AllMoviesUiEvent.OnShareClicked -> shareMovie(event.movie)
        }
    }

    private fun handleLikeClicked(movie: Movie) {
        viewModelScope.launch {
            val updatedFavoriteState = !movie.isFavorite
            moviesRepository.setMovie(movie.copy(isFavorite = updatedFavoriteState))

            setUiEffect {
                if (updatedFavoriteState)
                    AllMoviesUiEffect.ShowMessage(R.string.movie_liked)
                else
                    AllMoviesUiEffect.ShowMessage(R.string.movie_removed)
            }
        }
    }

    private fun shareMovie(movie: Movie) {
        setUiEffect {
            AllMoviesUiEffect.ShowShareMovie(movie)
        }
    }

}