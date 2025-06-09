package com.mtabarkevych.mymovie.movies.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mtabarkevych.mymovie.core.data.local.MyMovieDatabase
import com.mtabarkevych.mymovie.movies.data.local.model.MovieEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieDaoTest {

    private lateinit var database: MyMovieDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            MyMovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.movieDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetFavorites() = runBlocking {
        val movie = sampleMovie(id = 1, isFavorite = true)
        dao.insert(movie)
        val favorites = dao.getFavorites()
        assertEquals(listOf(movie), favorites)
    }

    @Test
    fun insertAllWithFavorites_preservesExistingFavorites() = runBlocking {
        val favorite = sampleMovie(id = 1, isFavorite = true)
        dao.insert(favorite)

        val updated = sampleMovie(id = 1, isFavorite = false)
        val newMovie = sampleMovie(id = 2, isFavorite = false)
        dao.insertAllWithFavorites(listOf(updated, newMovie))

        val favorites = dao.getFavorites()
        assertEquals(1, favorites.size)
        assertEquals(1, favorites[0].id)
        assertEquals(true, favorites[0].isFavorite)
    }

    @Test
    fun clearNotFavorites_removesNonFavoriteMovies() = runBlocking {
        val fav = sampleMovie(id = 1, isFavorite = true)
        val notFav = sampleMovie(id = 2, isFavorite = false)
        dao.insertAll(listOf(fav, notFav))
        dao.clearNotFavorites()
        val favorites = dao.getFavorites()
        assertEquals(listOf(fav), favorites)
    }

    private fun sampleMovie(id: Int, isFavorite: Boolean) = MovieEntity(
        id = id,
        title = "title$id",
        originalTitle = "orig$id",
        overview = "overview",
        posterPath = null,
        backdropPath = null,
        releaseDate = "2021-01-01",
        popularity = 0.0,
        voteAverage = 0.0,
        voteCount = 0,
        originalLanguage = "en",
        adult = false,
        video = false,
        isFavorite = isFavorite
    )
}