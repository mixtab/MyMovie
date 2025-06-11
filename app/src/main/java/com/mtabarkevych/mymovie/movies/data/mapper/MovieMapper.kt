package com.mtabarkevych.mymovie.movies.data.mapper

import com.mtabarkevych.mymovie.movies.data.local.model.MovieEntity
import com.mtabarkevych.mymovie.movies.data.remote.model.MovieDto
import com.mtabarkevych.mymovie.movies.domain.model.Movie

fun MovieDto.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        originalLanguage = originalLanguage,
        adult = adult,
        video = video,
        isFavorite = false,
    )
}


fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        originalLanguage = originalLanguage,
        adult = adult,
        video = video,
        isFavorite = isFavorite,
    )
}


fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        popularity = popularity,
        voteAverage = voteAverage,
        voteCount = voteCount,
        originalLanguage = originalLanguage,
        adult = adult,
        video = video,
        isFavorite = isFavorite,
    )
}










