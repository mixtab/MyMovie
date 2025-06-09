package com.mtabarkevych.mymovie.di.modules

import com.mtabarkevych.mymovie.movies.data.MoviesRepository
import com.mtabarkevych.mymovie.movies.domain.repository.IMoviesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::MoviesRepository).bind<IMoviesRepository>()
}