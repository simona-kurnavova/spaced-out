package com.kurnavova.spacedout.domain.usecase

import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.usecase.mapper.toArticle
import com.kurnavova.spacedout.domain.usecase.model.ArticleUseCaseResult
import com.kurnavova.spacedout.domain.usecase.mapper.toArticleUseCaseResult
import com.kurnavova.spacedout.domain.usecase.model.Article

/**
 * Use case for fetching articles.
 */
class FetchArticlesUseCase(
    private val repository: SpaceFlightRepository,
) {
    /**
     *  Fetches all articles.
     */
    suspend fun fetchAll(): ArticleUseCaseResult<List<Article>> {
        val result = repository.getNews()

        return result.toArticleUseCaseResult { data ->
            data.map { it.toArticle() }
        }
    }
}
