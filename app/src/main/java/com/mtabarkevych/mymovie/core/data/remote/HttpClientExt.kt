package com.mtabarkevych.mymovie.core.data.remote

import com.mtabarkevych.mymovie.core.domain.DataResult
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T : Any> safeCall(
    execute: () -> HttpResponse
): DataResult<T> {
    val response = try {
        execute()
    } catch(e: SocketTimeoutException) {
        return DataResult.Failure(RemoteDataError.REQUEST_TIMEOUT)
    } catch(e: UnresolvedAddressException) {
        return DataResult.Failure(RemoteDataError.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return DataResult.Failure(RemoteDataError.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T: Any> responseToResult(
    response: HttpResponse
): DataResult<T> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                DataResult.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                DataResult.Failure(RemoteDataError.SERIALIZATION)
            }
        }
        408 ->  DataResult.Failure(RemoteDataError.REQUEST_TIMEOUT)
        429 -> DataResult.Failure(RemoteDataError.TOO_MANY_REQUESTS)
        in 500..599 ->  DataResult.Failure(RemoteDataError.SERVER)
        else ->  DataResult.Failure(RemoteDataError.UNKNOWN)
    }
}


