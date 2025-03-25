package com.kurnavova.spacedout.features.newslist.usecase

import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.data.connectivity.NetworkStatusProvider
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.features.newslist.ui.Article
import com.kurnavova.spacedout.features.newslist.ui.mapper.toArticles
import com.kurnavova.spacedout.features.ui.ArticleUseCaseResult
import com.kurnavova.spacedout.features.ui.mapper.toArticleUseCaseResult
import kotlinx.collections.immutable.ImmutableList

/**
 * Use case for fetching articles.
 */
class FetchArticlesUseCase(
    private val repository: SpaceFlightRepository,
    private val networkStatusProvider: NetworkStatusProvider,
) {
    /**
     *  Fetches all articles.
     */
    suspend fun fetchAll(): ArticleUseCaseResult<ImmutableList<Article>> {
        if (!networkStatusProvider.hasInternet()) {
            return ArticleUseCaseResult.Error(R.string.error_network)
        }

        val result = repository.getNews()

        return result.toArticleUseCaseResult { it.toArticles() }
    }
}
