package com.mtabarkevych.mymovie.movies.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mtabarkevych.mymovie.core.data.local.MyMovieDatabase
import com.mtabarkevych.mymovie.core.domain.DataResult
import com.mtabarkevych.mymovie.movies.data.local.dao.MovieDao
import com.mtabarkevych.mymovie.movies.data.local.dao.RemoteKeysDao
import com.mtabarkevych.mymovie.movies.data.local.model.MovieEntity
import com.mtabarkevych.mymovie.movies.data.remote.model.MovieDto
import com.mtabarkevych.mymovie.movies.data.remote.response.MovieResponse
import com.mtabarkevych.mymovie.movies.data.remote.source.MoviesDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@OptIn(ExperimentalPagingApi::class)
@RunWith(AndroidJUnit4::class)
class MovieRemoteMediatorTest {

    private lateinit var db: MyMovieDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var remoteKeysDao: RemoteKeysDao
    private val moviesDataSource: MoviesDataSource = mockk()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyMovieDatabase::class.java
        ).allowMainThreadQueries().build()

        movieDao = db.movieDao
        remoteKeysDao = db.keyDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenDataIsPresent() = runTest {
        val fakeMovies = listOf(
            MovieDto( // sample fake movie
                id = 1, title = "Test", originalTitle = "Test",
                overview = "Overview", posterPath = null, backdropPath = null,
                releaseDate = "2020-01-01", popularity = 1.0, voteAverage = 8.5,
                voteCount = 100, originalLanguage = "en", adult = false, video = false,
                genreIds = listOf(1, 2, 3)
            )
        )

        val response = MovieResponse(1, results = fakeMovies, 1, 1)

        coEvery { moviesDataSource.getMovies(page = 1) } returns DataResult.Success(response)

        val mediator = MovieRemoteMediator(db, moviesDataSource)

        val pagingState = PagingState<Int, MovieEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val result = mediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}