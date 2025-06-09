package com.mtabarkevych.mymovie.core.data.remote

import com.mtabarkevych.mymovie.core.domain.DataError

enum class RemoteDataError: DataError {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER,
    SERIALIZATION,
    UNKNOWN
}