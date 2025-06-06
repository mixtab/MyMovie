package com.mtabarkevych.mymovie.movies.di.modules

import com.mtabarkevych.mymovie.movies.presentation.HomeViewModel
import com.mtabarkevych.mymovie.movies.presentation.favorites.FavoritesViewModel
import com.mtabarkevych.mymovie.movies.presentation.all_movies.AllMoviesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::AllMoviesViewModel)
}