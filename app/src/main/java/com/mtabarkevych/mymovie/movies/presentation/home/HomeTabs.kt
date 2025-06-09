package com.mtabarkevych.mymovie.movies.presentation.home

import androidx.annotation.StringRes
import com.mtabarkevych.mymovie.R

enum class HomeTabs(
    @StringRes val titleRes: Int
) {
    ALL_MOVIES(
        R.string.all_movies_title
    ),
    FAVORITES(
        R.string.favorites_title
    )
}