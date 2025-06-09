package com.mtabarkevych.mymovie.di.modules

import com.mtabarkevych.mymovie.core.data.remote.HttpClientFactory
import com.mtabarkevych.mymovie.movies.data.remote.source.MoviesDataSource
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single { HttpClientFactory.create(get()) }
    singleOf(::MoviesDataSource)
}