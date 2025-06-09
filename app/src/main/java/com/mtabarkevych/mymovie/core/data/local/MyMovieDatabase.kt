package com.mtabarkevych.mymovie.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mtabarkevych.mymovie.movies.data.local.dao.MovieDao
import com.mtabarkevych.mymovie.movies.data.local.dao.RemoteKeysDao
import com.mtabarkevych.mymovie.movies.data.local.model.MovieEntity
import com.mtabarkevych.mymovie.movies.data.local.model.RemoteKeysEntity

@Database(
    entities = [MovieEntity::class, RemoteKeysEntity::class],
    version = 1
)

abstract class MyMovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val keyDao: RemoteKeysDao

    companion object {
        const val DB_NAME = "movie.db"
    }
}

