package com.mtabarkevych.mymovie.core.domain

interface DataError

sealed class DataResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Failure(val error: DataError) : DataResult<Nothing>()
}

val <T : Any> DataResult<T>.isSuccess
    get() = this is DataResult.Success

val <T : Any> DataResult<T>.isFailure
    get() = this is DataResult.Failure

inline fun <T : Any> DataResult<T>.onSuccess(block: (T) -> Unit): DataResult<T> {
    if (isSuccess) block((this as DataResult.Success<T>).data)
    return this
}

inline fun <T : Any> DataResult<T>.onFailure(block: (DataError) -> Unit): DataResult<T> {
    if (isFailure) block((this as DataResult.Failure).error)
    return this
}

inline fun <T : Any, R : Any> DataResult<T>.map(transform: (T) -> R): DataResult<R> {
    return when (this) {
        is DataResult.Success -> DataResult.Success(transform(this.data))
        is DataResult.Failure -> this
    }
}

inline fun <T : Any> DataResult<T>.getOr(block: (error: DataResult.Failure) -> T): T {
    return when (this) {
        is DataResult.Failure -> block(this)
        is DataResult.Success -> this.data
    }
}

fun <T : Any> DataResult<T>.getOrNull(): T? {
    return when (this) {
        is DataResult.Failure -> null
        is DataResult.Success -> this.data
    }
}

fun <R : Any, T : R> DataResult<T>.getOrDefault(defaultValue: R): R {
    if (isFailure) return defaultValue
    return (this as DataResult.Success<T>).data
}
