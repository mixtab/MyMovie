package com.mtabarkevych.mymovie.movies.data.remote.source

import com.mtabarkevych.mymovie.core.data.remote.RemoteDataError
import com.mtabarkevych.mymovie.core.domain.DataResult
import com.mtabarkevych.mymovie.movies.data.remote.model.MovieDto
import com.mtabarkevych.mymovie.movies.data.remote.response.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.Assert.*
import org.junit.Test

class MoviesDataSourceTest {

    private fun buildClient(engine: MockEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Test
    fun `getMovies returns success for valid response`() = runBlocking {
        val json = Json.encodeToJsonElement(
            MovieResponse(
                1, listOf(
                    MovieDto(
                        id = 1,
                        title = "Test Movie",
                        originalTitle = "Test Movie",
                        overview = "Overview",
                        posterPath = null,
                        backdropPath = null,
                        releaseDate = "2025-05-08",
                        popularity = 100.0,
                        voteAverage = 8.5,
                        voteCount = 100,
                        originalLanguage = "en",
                        adult = false,
                        video = false,
                        genreIds = listOf(1,2,3)
                    )
                ), 1, 1
            )
        ).toString()

        val engine = MockEngine {
            respond(
                json,
                HttpStatusCode.OK,
                headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = buildClient(engine)
        val dataSource = MoviesDataSource(client)

        val result = dataSource.getMovies()

        assertTrue(result is DataResult.Success)
        result as DataResult.Success
        assertEquals(1, result.data.page)
        assertEquals(1, result.data.results.size)
    }

    @Test
    fun `getMovies returns failure for error response`() = runBlocking {
        val engine = MockEngine { respond("error", HttpStatusCode.InternalServerError) }
        val client = buildClient(engine)
        val dataSource = MoviesDataSource(client)

        val result = dataSource.getMovies()

        assertTrue(result is DataResult.Failure)
        result as DataResult.Failure
        assertEquals(RemoteDataError.SERVER, result.error)
    }
}