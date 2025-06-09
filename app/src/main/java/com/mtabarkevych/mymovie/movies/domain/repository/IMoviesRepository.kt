package com.mtabarkevych.mymovie.movies.domain.repository

import com.mtabarkevych.mymovie.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    suspend fun setMovie(movie: Movie)

    fun getFavorites(): Flow<List<Movie>>


}