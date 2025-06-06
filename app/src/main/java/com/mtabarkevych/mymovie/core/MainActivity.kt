package com.mtabarkevych.mymovie.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import com.mtabarkevych.mymovie.core.theme.MyMovieTheme
import com.mtabarkevych.mymovie.movies.presentation.HomeScreenRoute

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyMovieTheme {
                Box{
                    HomeScreenRoute()
                }
            }
        }
    }
}
