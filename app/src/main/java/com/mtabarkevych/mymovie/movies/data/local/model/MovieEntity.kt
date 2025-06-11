package com.mtabarkevych.mymovie.movies.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("original_title") val originalTitle: String,
    @ColumnInfo("overview") val overview: String,
    @ColumnInfo("poster_path") val posterPath: String?,
    @ColumnInfo("backdrop_path") val backdropPath: String?,
    @ColumnInfo("release_date") val releaseDate: String,
    @ColumnInfo("popularity") val popularity: Double,
    @ColumnInfo("vote_average") val voteAverage: Double,
    @ColumnInfo("vote_count") val voteCount: Int,
    @ColumnInfo("original_language") val originalLanguage: String,
    @ColumnInfo("adult") val adult: Boolean,
    @ColumnInfo("video") val video: Boolean,
    @ColumnInfo("is_favorite") val isFavorite: Boolean,
)