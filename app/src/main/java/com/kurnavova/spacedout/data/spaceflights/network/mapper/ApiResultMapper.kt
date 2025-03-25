package com.kurnavova.spacedout.data.spaceflights.network.mapper

import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.ErrorType
import retrofit2.Response

/**
 * Maps a Retrofit response to an API result.
 *
 * @param processBody The function to process the body of the response.
 */
fun <T, R> Response<T>.toApiResult(processBody: (T) -> R): ApiResult<R> {
    return if (this.isSuccessful) {
        body()
            ?.let { ApiResult.Success(processBody(it)) }
            ?: ApiResult.Error(ErrorType.Unknown)
    } else {
        when (code()) {
            in 400..419 -> ApiResult.Error(ErrorType.Client)
            in 500..519 -> ApiResult.Error(ErrorType.Server)
            408 -> ApiResult.Error(ErrorType.Network) // Request Timeout
            else -> ApiResult.Error(ErrorType.Unknown) // Other unknown errors
        }
    }
}
