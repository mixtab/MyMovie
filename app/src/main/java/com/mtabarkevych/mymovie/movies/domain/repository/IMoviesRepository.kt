package com.mtabarkevych.mymovie.movies.domain.repository

import androidx.paging.PagingData
import com.mtabarkevych.mymovie.movies.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    fun getAllMovies(): Flow<PagingData<Movie>>

    suspend fun setMovie(movie: Movie)

    fun getFavorites(): Flow<List<Movie>>


}