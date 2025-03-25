package com.kurnavova.spacedout.features.newsdetail.usecase

import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.data.connectivity.NetworkStatusProvider
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.features.newsdetail.ui.ArticleUiDetail
import com.kurnavova.spacedout.features.newsdetail.ui.mapper.toArticleUiDetail
import com.kurnavova.spacedout.features.ui.ArticleUseCaseResult
import com.kurnavova.spacedout.features.ui.mapper.toArticleUseCaseResult

/**
 * Use case for fetching an article by its unique identifier.
 */
class FetchArticleDetailUseCase(
    private val repository: SpaceFlightRepository,
    private val networkStatusProvider: NetworkStatusProvider,
) {
    /**
     * Fetches an article by its unique identifier.
     *
     * @param id The unique identifier of the article.
     */
    suspend fun fetchArticle(id: Int): ArticleUseCaseResult<ArticleUiDetail> {
        if (!networkStatusProvider.hasInternet()) {
            return ArticleUseCaseResult.Error(R.string.error_network)
        }

        val result = repository.getArticleById(id)
        return result.toArticleUseCaseResult { article -> article.toArticleUiDetail() }
    }
}
