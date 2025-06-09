package com.mtabarkevych.mymovie.movies.presentation.extensions

import android.content.Context
import android.content.Intent
import com.mtabarkevych.mymovie.movies.domain.model.Movie

fun Movie.shareMovie(context: Context) {
    val shareText =
        "$originalTitle - $overview" + "\n Movie poster: https://image.tmdb.org/t/p/w342${posterPath}"

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Share movie with"))
}