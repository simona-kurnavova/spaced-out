package com.kurnavova.spacedout.features.ui

import androidx.annotation.StringRes

/**
 * Represents the result of a use case for article data.
 */
sealed interface ArticleUseCaseResult<T> {
    /**
     * Represents a successful result.
     *
     * @param data The data returned by the use case.
     */
    data class Success<T>(val data: T) : ArticleUseCaseResult<T>

    /**
     * Represents an error result.
     *
     * @param errorMessage The error message resource ID.
     */
    data class Error<T>(@StringRes val errorMessage: Int) : ArticleUseCaseResult<T>
}
