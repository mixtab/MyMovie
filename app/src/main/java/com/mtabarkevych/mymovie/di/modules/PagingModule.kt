package com.mtabarkevych.mymovie.di.modules

import com.mtabarkevych.mymovie.movies.data.mediator.MovieRemoteMediator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val pagingModule = module {
    singleOf(::MovieRemoteMediator)
}