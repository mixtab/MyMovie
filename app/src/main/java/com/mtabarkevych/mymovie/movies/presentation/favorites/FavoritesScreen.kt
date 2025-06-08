package com.mtabarkevych.mymovie.movies.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtabarkevych.mymovie.movies.presentation.MovieCard
import com.mtabarkevych.mymovie.movies.presentation.extensions.toFormattedMonthYear
import org.koin.androidx.compose.koinViewModel


@Composable
fun FavoritesScreenRoute(
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    FavoritesEffectHandler(viewModel.effect)
    FavoritesScreen(
        uiState, processUiEvent = {
            viewModel.sendUiEvent(it)
        }
    )
}

@Composable
fun FavoritesScreen(
    uiState: FavoritesUiState,
    processUiEvent: (FavoritesUiEvent) -> Unit
) {

    Box(Modifier.fillMaxSize()) {
        if (uiState.favoriteMovies.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(uiState.favoriteMovies) {index, movie ->
                    val previousMovieDate = if (index == 0) null else uiState.favoriteMovies[index.dec()].releaseDate.toFormattedMonthYear()

                    val currentHeader = movie.releaseDate.toFormattedMonthYear()
                    if (currentHeader != previousMovieDate) {

                        Text(
                            text = currentHeader,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                    }

                    MovieCard(
                        movie,
                        onToggleFavorite = {
                            processUiEvent.invoke(FavoritesUiEvent.OnUnlikeClicked(movie))
                        }, onShare = {
                            processUiEvent.invoke(FavoritesUiEvent.OnShareClicked(movie))
                        }
                    )
                }
            }
        } else {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Faforites is empty",
                style = MaterialTheme.typography.titleMedium
            )

        }
    }

}

@Preview
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(FavoritesUiState()) {}
}