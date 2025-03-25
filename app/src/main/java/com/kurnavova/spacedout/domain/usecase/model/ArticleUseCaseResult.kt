package com.kurnavova.spacedout.domain.usecase.model

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
     * @param error The type of error that occurred.
     */
    data class Error<T>(val error: UseCaseError) : ArticleUseCaseResult<T>
}
