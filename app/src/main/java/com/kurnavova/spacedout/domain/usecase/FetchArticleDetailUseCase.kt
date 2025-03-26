package com.kurnavova.spacedout.domain.usecase

import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.usecase.mapper.toArticle
import com.kurnavova.spacedout.domain.usecase.model.ArticleUseCaseResult
import com.kurnavova.spacedout.domain.usecase.mapper.toArticleUseCaseResult
import com.kurnavova.spacedout.domain.usecase.model.Article

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
    suspend fun fetchArticle(id: Int): ArticleUseCaseResult<Article> {
        val result = repository.getArticleById(id)
        val article = result.toArticleUseCaseResult { it.toArticle() }
        return article
    }
}
