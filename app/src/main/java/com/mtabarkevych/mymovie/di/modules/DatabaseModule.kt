package com.mtabarkevych.mymovie.di.modules

import androidx.room.Room
import com.mtabarkevych.mymovie.core.data.local.MyMovieDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(), MyMovieDatabase::class.java, MyMovieDatabase.DB_NAME
        ).build()
    }

    single { get<MyMovieDatabase>().movieDao }
    single { get<MyMovieDatabase>().keyDao}
}