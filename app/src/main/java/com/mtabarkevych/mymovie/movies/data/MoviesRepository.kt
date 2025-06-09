package com.mtabarkevych.mymovie.movies.data

import com.mtabarkevych.mymovie.movies.data.local.dao.MovieDao
import com.mtabarkevych.mymovie.movies.data.remote.source.MoviesDataSource
import com.mtabarkevych.mymovie.movies.domain.model.Movie
import com.mtabarkevych.mymovie.movies.domain.model.mapper.toDomain
import com.mtabarkevych.mymovie.movies.domain.model.mapper.toEntity
import com.mtabarkevych.mymovie.movies.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepository(
    private val moviesDataSource: MoviesDataSource,
    private val movieDao: MovieDao
) : IMoviesRepository {

    override suspend fun setMovie(movie: Movie) {
        movieDao.insert(movie.toEntity())
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return movieDao.getFavoritesFlow().map { it.map { it.toDomain() } }
    }


}