package com.mtabarkevych.mymovie.core

import android.app.Application
import com.mtabarkevych.mymovie.movies.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApp)
        }
    }
}