package com.mtabarkevych.mymovie.movies.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mtabarkevych.mymovie.core.data.local.MyMovieDatabase
import com.mtabarkevych.mymovie.core.domain.DataResult
import com.mtabarkevych.mymovie.movies.data.local.model.MovieEntity
import com.mtabarkevych.mymovie.movies.data.local.model.RemoteKeysEntity
import com.mtabarkevych.mymovie.movies.data.remote.source.MoviesDataSource
import com.mtabarkevych.mymovie.movies.domain.model.mapper.toEntity

@OptIn(ExperimentalPagingApi::class)
open class MovieRemoteMediator(
    private val db: MyMovieDatabase,
    private val moviesDataSource: MoviesDataSource,
) : RemoteMediator<Int, MovieEntity>() {

    private val remoteKeysDao = db.keyDao
    private val movieDao = db.movieDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?:0
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                nextKey
            }
        }

        return try {
            when (val result = moviesDataSource.getMovies(page)) {
                is DataResult.Success -> {
                    val movies = result.data.results.map { it.toEntity() }
                    val endOfPaginationReached = movies.isEmpty()

                    db.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            movieDao.clearNotFavorites()
                            remoteKeysDao.clearRemoteKeys()
                        }

                        val keys = movies.mapIndexed { index, movie ->
                            RemoteKeysEntity(
                                movieId = movie.id,
                                prevKey = if (page == 1) null else page - 1,
                                nextKey = if (endOfPaginationReached) null else page + 1,
                                order = ((page - 1) * state.config.pageSize) + index
                            )
                        }

                        remoteKeysDao.insertAll(keys)
                        movieDao.insertAllWithFavorites(movies)
                    }

                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }

                is DataResult.Failure -> MediatorResult.Error(Exception("API error: ${result.error}"))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeysEntity? {
        return state.lastItemOrNull()?.let { movie ->
            remoteKeysDao.remoteKeysMovieId(movie.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> remoteKeysDao.remoteKeysMovieId(movie.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.remoteKeysMovieId(id)
            }
        }
    }
}
