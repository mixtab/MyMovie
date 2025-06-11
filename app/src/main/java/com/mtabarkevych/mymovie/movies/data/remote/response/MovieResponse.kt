package com.mtabarkevych.mymovie.movies.data.remote.response

import com.mtabarkevych.mymovie.movies.data.remote.model.MovieDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("page")val page: Int,
    @SerialName("results")val results: List<MovieDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)