package com.mtabarkevych.mymovie.movies.presentation.favorites

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mtabarkevych.mymovie.core.presentation.extensions.collectWithLifecycle
import com.mtabarkevych.mymovie.movies.presentation.extensions.shareMovie
import kotlinx.coroutines.flow.Flow


@Composable
fun FavoritesEffectHandler(
    effects: Flow<FavoritesUiEffect>,
) {
    val context = LocalContext.current

    effects.collectWithLifecycle { effect ->
        when (effect) {
            is FavoritesUiEffect.ShowMessage -> {
                Toast.makeText(context, effect.stringRes, Toast.LENGTH_SHORT).show()
            }

            is FavoritesUiEffect.ShowShareMovie -> effect.movie.shareMovie(context)
        }
    }
}