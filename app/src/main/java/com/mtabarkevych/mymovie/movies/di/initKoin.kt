package com.mtabarkevych.mymovie.movies.di

import com.mtabarkevych.mymovie.movies.di.modules.repositoryModule
import com.mtabarkevych.mymovie.movies.di.modules.useCaseModule
import com.mtabarkevych.mymovie.movies.di.modules.viewModelModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {

    val sharedModule = listOf(
        viewModelModule,
        useCaseModule,
        repositoryModule
    )

    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}