package com.mtabarkevych.mymovie.movies.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mtabarkevych.mymovie.movies.presentation.all_movies.AllMoviesScreenRoute
import com.mtabarkevych.mymovie.movies.presentation.favorites.FavoritesScreen
import com.mtabarkevych.mymovie.movies.presentation.favorites.FavoritesScreenRoute
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoute(viewModel: HomeViewModel = koinViewModel()) {
    val state = viewModel.uiState.collectAsState().value

    HomeScreen(state) { viewModel.sendUiEvent(it) }
}


@Composable
fun HomeScreen(uiState: HomeUiState, processUiEvent: (HomeUiEvent) -> Unit) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val pagerState = rememberPagerState { uiState.tabs.size }
        val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            uiState.tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    text = {
                        Text(text = stringResource(tab.titleRes))
                    },
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(tab.ordinal)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (uiState.tabs[selectedTabIndex.value]) {
                HomeTabs.ALL_MOVIES -> {
                    AllMoviesScreenRoute()
                }

                HomeTabs.FAVORITES -> {
                    FavoritesScreenRoute()
                }
            }

        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(HomeUiState()) {}

}