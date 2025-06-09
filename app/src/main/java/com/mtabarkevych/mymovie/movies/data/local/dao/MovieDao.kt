package com.mtabarkevych.mymovie.movies.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtabarkevych.mymovie.movies.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    suspend fun insertAllWithFavorites(movies: List<MovieEntity>) {

        val existingFavorites = getFavorites()

        val favoriteMap = existingFavorites.associateBy({ it.id }, { it.isFavorite })

        val updatedMovies = movies.map { movie ->
            movie.copy(
                isFavorite = favoriteMap[movie.id] ?: movie.isFavorite
            )
        }
        insertAll(updatedMovies)
    }


    @Query(
        """
        SELECT movies.*
        FROM movies
        INNER JOIN remote_keys ON movies.id = remote_keys.movieId
        ORDER BY `order` ASC
        """
    )
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE isFavorite = 1 ORDER BY releaseDate ASC")
    fun getFavorites(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE isFavorite = 1 ORDER BY releaseDate ASC")
    fun getFavoritesFlow(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavorite(movieId: Int, isFavorite: Boolean)

    @Query("DELETE FROM movies WHERE isFavorite = 0")
    suspend fun clearNotFavorites()
}

