package com.mtabarkevych.mymovie.di.modules

import com.mtabarkevych.mymovie.movies.domain.usecase.GetAllMoviesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::GetAllMoviesUseCase)
}