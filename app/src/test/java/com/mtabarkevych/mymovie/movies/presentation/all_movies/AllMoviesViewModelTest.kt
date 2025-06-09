package com.mtabarkevych.mymovie.movies.presentation.all_movies

import app.cash.turbine.test
import com.mtabarkevych.mymovie.movies.data.MoviesRepository
import com.mtabarkevych.mymovie.movies.domain.model.Movie
import com.mtabarkevych.mymovie.movies.domain.usecase.GetAllMoviesUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AllMoviesViewModelTest {

    private lateinit var viewModel: AllMoviesViewModel

    private val fakeUseCase = mockk<GetAllMoviesUseCase>(relaxed = true)
    private val repository = mockk<MoviesRepository>(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

   private val movie = Movie(
        id = 1,
        title = "Test Movie",
        originalTitle = "Test Movie",
        overview = "Overview",
        posterPath = null,
        backdropPath = null,
        releaseDate = "2025-05-08",
        popularity = 100.0,
        voteAverage = 8.5,
        voteCount = 100,
        originalLanguage = "en",
        adult = false,
        video = false,
        isFavorite = false
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { fakeUseCase.invoke() } returns emptyFlow()

        viewModel = AllMoviesViewModel(
            getAllMoviesUseCase = fakeUseCase,
            moviesRepository = repository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `OnLikeClicked should toggle favorite and emit effect`() = runTest {

        viewModel.effect.test {
            viewModel.sendUiEvent(AllMoviesUiEvent.OnLikeClicked(movie))
            advanceUntilIdle()

            coVerify {
                repository.setMovie(movie.copy(isFavorite = true))
            }

            val effect = awaitItem()
            assertTrue(effect is AllMoviesUiEffect.ShowMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `OnShareClicked should emit share effect`() = runTest {

        viewModel.effect.test {
            viewModel.sendUiEvent(AllMoviesUiEvent.OnShareClicked(movie))

            val effect = awaitItem()
            assertTrue(effect is AllMoviesUiEffect.ShowShareMovie)
            assertEquals(movie, (effect as AllMoviesUiEffect.ShowShareMovie).movie)
            cancelAndIgnoreRemainingEvents()
        }
    }
}