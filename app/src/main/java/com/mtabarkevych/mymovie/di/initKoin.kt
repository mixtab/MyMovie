package com.mtabarkevych.mymovie.di

import com.mtabarkevych.mymovie.di.modules.dataSourceModule
import com.mtabarkevych.mymovie.di.modules.databaseModule
import com.mtabarkevych.mymovie.di.modules.pagingModule
import com.mtabarkevych.mymovie.di.modules.repositoryModule
import com.mtabarkevych.mymovie.di.modules.useCaseModule
import com.mtabarkevych.mymovie.di.modules.viewModelModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {

    val sharedModule = listOf(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        dataSourceModule,
        databaseModule,
        pagingModule
    )

    startKoin {
        config?.invoke(this)
        modules(sharedModule)
    }
}