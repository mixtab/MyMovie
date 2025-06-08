package com.mtabarkevych.mymovie.movies.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.mtabarkevych.mymovie.movies.data.local.dao.MovieDao
import com.mtabarkevych.mymovie.movies.data.mediator.MovieRemoteMediator
import com.mtabarkevych.mymovie.movies.domain.model.mapper.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map

class GetAllMoviesUseCase(
    private val mediator: MovieRemoteMediator,
    private val db: MovieDao,
) {
    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(scope: CoroutineScope) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        remoteMediator = mediator,
        pagingSourceFactory = { db.pagingSource() }
    ).flow

        .map { pagingData -> pagingData.map { it.toDomain() } }
        .cachedIn(scope)
}