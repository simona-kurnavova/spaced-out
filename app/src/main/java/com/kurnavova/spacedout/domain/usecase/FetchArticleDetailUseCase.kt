package com.kurnavova.spacedout.domain.usecase

import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.ArticleDetail

/**
 * Use case for fetching an article by its unique identifier.
 */
class FetchArticleDetailUseCase(
    private val repository: SpaceFlightRepository,
) {
    /**
     * Fetches an article by its unique identifier.
     *
     * @param id The unique identifier of the article.
     */
    suspend fun invoke(id: Int): ApiResult<ArticleDetail> = repository.getArticleById(id)
}
