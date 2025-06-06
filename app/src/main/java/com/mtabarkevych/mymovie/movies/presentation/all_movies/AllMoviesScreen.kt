package com.mtabarkevych.mymovie.movies.presentation.all_movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AllMoviesScreenRoute() {
    AllMoviesScreen()
}

@Composable
fun AllMoviesScreen() {
    Box(
        Modifier.fillMaxSize()
    ) {
        Text(text = "All Movies", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun AllMoviesScreenPreview() {
    AllMoviesScreen()
}