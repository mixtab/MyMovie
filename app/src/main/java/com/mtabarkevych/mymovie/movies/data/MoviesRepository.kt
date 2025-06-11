package com.mtabarkevych.mymovie.movies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mtabarkevych.mymovie.movies.data.local.dao.MovieDao
import com.mtabarkevych.mymovie.movies.data.local.dao.RemoteKeysDao
import com.mtabarkevych.mymovie.movies.domain.model.Movie
import com.mtabarkevych.mymovie.movies.data.mapper.toDomain
import com.mtabarkevych.mymovie.movies.data.mapper.toEntity
import com.mtabarkevych.mymovie.movies.data.mediator.MovieRemoteMediator
import com.mtabarkevych.mymovie.movies.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepository(
    private val movieDao: MovieDao,
    private val mediator: MovieRemoteMediator
) : IMoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            remoteMediator = mediator,
            pagingSourceFactory = { movieDao.pagingSource() }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override suspend fun setMovie(movie: Movie) {
        movieDao.insert(movie.toEntity())
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return movieDao.getFavoritesFlow().map { it.map { it.toDomain() } }
    }
}