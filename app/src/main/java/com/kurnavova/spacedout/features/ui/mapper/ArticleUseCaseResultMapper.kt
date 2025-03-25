package com.kurnavova.spacedout.features.ui.mapper

import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.features.ui.ArticleUseCaseResult

/**
 * Maps an [ApiResult] to [ArticleUseCaseResult].
 *
 * @param convertData The function to convert the data from the API result to the desired type of result.
 */
fun <T, R> ApiResult<T>.toArticleUseCaseResult(
    convertData: (T) -> R
): ArticleUseCaseResult<R> = when(this) {
    is ApiResult.Success -> ArticleUseCaseResult.Success(convertData(data))
    is ApiResult.Error -> ArticleUseCaseResult<R>.Error(toErrorMessage())
}
