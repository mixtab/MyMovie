package com.mtabarkevych.mymovie.movies.data

import com.mtabarkevych.mymovie.movies.data.local.dao.MovieDao
import com.mtabarkevych.mymovie.movies.data.local.model.MovieEntity
import com.mtabarkevych.mymovie.movies.domain.model.Movie
import com.mtabarkevych.mymovie.movies.data.mapper.toDomain
import com.mtabarkevych.mymovie.movies.data.mapper.toEntity
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import org.junit.Assert.*
import org.mockito.kotlin.*

class MoviesRepositoryTest {

    @Mock
    private lateinit var movieDao: MovieDao

    private lateinit var moviesRepository: MoviesRepository

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
        MockitoAnnotations.openMocks(this)
        moviesRepository = MoviesRepository(movieDao)
    }

    @Test
    fun `setMovie should call movieDao insert with converted entity`() = runTest {
        val expectedEntity = movie.toEntity()

        moviesRepository.setMovie(movie)

        verify(movieDao).insert(expectedEntity)
    }

    @Test
    fun `setMovie should handle null values correctly`() = runTest {
        moviesRepository.setMovie(movie)
        verify(movieDao).insert(any())
    }

    @Test
    fun `getFavorites should return mapped domain objects from dao`() = runTest {
        val movieEntity1 = MovieEntity(
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
        val movieEntity2 = MovieEntity(
            id = 2,
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
            isFavorite = true
        )
        val entityList = listOf(movieEntity1, movieEntity2)
        val expectedDomainList = entityList.map { it.toDomain() }

        whenever(movieDao.getFavoritesFlow()).thenReturn(flowOf(entityList))

        val result = moviesRepository.getFavorites().first()

        assertEquals(expectedDomainList, result)
        verify(movieDao).getFavoritesFlow()
    }

    @Test
    fun `getFavorites should return empty list when dao returns empty flow`() = runTest {
        whenever(movieDao.getFavoritesFlow()).thenReturn(flowOf(emptyList()))

        val result = moviesRepository.getFavorites().first()

        assertTrue(result.isEmpty())
        verify(movieDao).getFavoritesFlow()
    }

    @Test
    fun `getFavorites should handle single favorite movie`() = runTest {
        // Given
        val movie = Movie(
            adult = false,
            backdropPath = null,
            id = 1,
            originalLanguage = "en",
            originalTitle = "Test Movie",
            overview = "Overview",
            popularity = 100.0,
            posterPath = null,
            releaseDate = "2025-05-08",
            title = "Test Movie",
            video = false,
            voteAverage = 8.5,
            voteCount = 100,
            isFavorite = false
        )
        val movieEntity = movie.toEntity()
        val expectedDomain = movie
        whenever(movieDao.getFavoritesFlow()).thenReturn(flowOf(listOf(movieEntity)))
        val result = moviesRepository.getFavorites().first()

        assertEquals(1, result.size)
        assertEquals(expectedDomain, result.first())
    }

    @Test
    fun `repository should not modify dao behavior`() = runTest {

        moviesRepository.setMovie(movie)

        verify(movieDao, times(1)).insert(any())
        verifyNoMoreInteractions(movieDao)
    }
}