package com.kurnavova.spacedout.domain.model

/**
 * A generic sealed class for handling API responses.
 */
sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val type: ErrorType) : ApiResult<Nothing>
}

/**
 * Represents the type of error that occurred.
 */
enum class ErrorType {
    Server,
    Client,
    Network,
    Unknown,
}
