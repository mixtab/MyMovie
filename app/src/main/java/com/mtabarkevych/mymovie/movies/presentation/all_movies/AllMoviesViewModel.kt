package com.mtabarkevych.mymovie.movies.presentation.all_movies

import androidx.lifecycle.viewModelScope
import com.mtabarkevych.mymovie.R
import com.mtabarkevych.mymovie.core.presentation.MVIViewModel
import com.mtabarkevych.mymovie.movies.data.MoviesRepository
import com.mtabarkevych.mymovie.movies.domain.usecase.GetAllMoviesUseCase
import com.mtabarkevych.mymovie.movies.presentation.favorites.FavoritesUiEffect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllMoviesViewModel(
    getAllMoviesUseCase: GetAllMoviesUseCase,
    private val moviesRepository: MoviesRepository
) : MVIViewModel<AllMoviesUiState, AllMoviesUiEvent, AllMoviesUiEffect>() {

    val movies = getAllMoviesUseCase(viewModelScope)

    private val _uiState = MutableStateFlow(AllMoviesUiState())
    override val uiState = _uiState.asStateFlow()


    override fun processUiEvent(event: AllMoviesUiEvent) {
        when (event) {
            is AllMoviesUiEvent.OnLikeClicked -> {
                viewModelScope.launch {
                    val updatedFavoriteState = !event.movie.isFavorite
                    moviesRepository.setMovie(event.movie.copy(isFavorite = updatedFavoriteState))

                    setUiEffect {
                        if (updatedFavoriteState)
                            AllMoviesUiEffect.ShowMessage(R.string.movie_removed)
                        else
                            AllMoviesUiEffect.ShowMessage(R.string.movie_removed)
                    }
                }
            }

            is AllMoviesUiEvent.OnShareClicked -> setUiEffect {
                AllMoviesUiEffect.ShowShareMovie(event.movie)
            }
        }
    }

}