package com.mtabarkevych.mymovie.movies.data.remote.source

import com.mtabarkevych.mymovie.BuildConfig
import com.mtabarkevych.mymovie.core.data.remote.safeCall
import com.mtabarkevych.mymovie.core.domain.DataResult
import com.mtabarkevych.mymovie.movies.data.remote.response.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

class MoviesDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getMovies(
        page: Int = 1,
        minVoteCount:Int = 100,
        minVoteAvarage:Int = 7,
        sortBy: String = "primary_release_date.desc"
    ): DataResult<MovieResponse> {
        return safeCall<MovieResponse> {
            httpClient.get("$MOVIE_URL/3/discover/movie") {
                header(HttpHeaders.Authorization, "Bearer ${BuildConfig.MOVIE_API_KEY}")
                header(HttpHeaders.Accept, "application/json")
                url {
                    parameters.append("vote_average.gte", minVoteAvarage.toString())
                    parameters.append("vote_count.gte", minVoteCount.toString())
                    parameters.append("sort_by", sortBy)
                    parameters.append("page", page.toString())
                }
            }
        }
    }


    companion object {
        const val MOVIE_URL = "https://api.themoviedb.org"
    }

}