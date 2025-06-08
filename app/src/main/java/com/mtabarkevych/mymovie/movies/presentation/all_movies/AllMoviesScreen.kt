package com.mtabarkevych.mymovie.movies.presentation.all_movies

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.mtabarkevych.mymovie.core.presentation.ui.PullToRefreshBox
import com.mtabarkevych.mymovie.movies.domain.model.Movie
import com.mtabarkevych.mymovie.movies.presentation.MovieCard
import com.mtabarkevych.mymovie.movies.presentation.extensions.toFormattedMonthYear
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel


@Composable
fun AllMoviesScreenRoute(viewModel: AllMoviesViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value
    val movies = viewModel.movies.collectAsLazyPagingItems()

    AllMoviesEffectHandler(viewModel.effect)
    AllMoviesScreen(
        uiState, movies, processUiEvent = {
            viewModel.sendUiEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllMoviesScreen(
    uiState: AllMoviesUiState,
    movies: LazyPagingItems<Movie>,
    processUiEvent: (AllMoviesUiEvent) -> Unit
) {
    val refreshing = movies.loadState.refresh is LoadState.Loading

    PullToRefreshBox(
        isRefreshing = refreshing,
        onRefresh = {
            movies.refresh()
        }
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                count = movies.itemCount,
                key = movies.itemKey { it.id }
            ) { index ->
                val previousMovieDate =
                    if (index == 0) null else movies[index.dec()]?.releaseDate?.toFormattedMonthYear()
                val movie = movies[index] ?: return@items

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
                        processUiEvent.invoke(AllMoviesUiEvent.OnLikeClicked(movie))
                    }, onShare = {
                        processUiEvent.invoke(AllMoviesUiEvent.OnShareClicked(movie))
                    }
                )
            }

            when (val append = movies.loadState.append) {
                is LoadState.Loading -> item {
                    CircularProgressIndicator(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }

                is LoadState.Error -> item {
                    Text(
                        text = "Error: ${append.error.message}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> Unit
            }
        }
    }
}

@Preview
@Composable
fun AllMoviesScreenPreview() {
    AllMoviesScreen(AllMoviesUiState(), fakeLazyPagingItems(listOf()), {})
}

@Composable
fun <T : Any> fakeLazyPagingItems(items: List<T>): LazyPagingItems<T> {
    return flowOf(PagingData.from(items)).collectAsLazyPagingItems()
}
