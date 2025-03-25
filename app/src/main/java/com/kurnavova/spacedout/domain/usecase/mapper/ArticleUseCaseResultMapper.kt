package com.kurnavova.spacedout.domain.usecase.mapper

import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.ErrorType
import com.kurnavova.spacedout.domain.usecase.model.ArticleUseCaseResult
import com.kurnavova.spacedout.domain.usecase.model.UseCaseError

/**
 * Maps an [ApiResult] to [ArticleUseCaseResult].
 *
 * @param convertData The function to convert the data from the API result to the desired type of result.
 */
fun <T, R> ApiResult<T>.toArticleUseCaseResult(
    convertData: (T) -> R
): ArticleUseCaseResult<R> = when(this) {
    is ApiResult.Success -> ArticleUseCaseResult.Success(convertData(data))
    is ApiResult.Error -> ArticleUseCaseResult.Error(toErrorMessage())
}

fun ApiResult.Error.toErrorMessage(): UseCaseError = when(type) {
    ErrorType.Server -> UseCaseError.Server
    ErrorType.Client -> UseCaseError.Client
    ErrorType.Network -> UseCaseError.Network
    ErrorType.Unknown -> UseCaseError.Unknown
}
