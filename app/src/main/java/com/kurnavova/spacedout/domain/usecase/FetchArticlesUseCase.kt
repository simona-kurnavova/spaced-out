package com.kurnavova.spacedout.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.usecase.mapper.toArticle
import com.kurnavova.spacedout.domain.usecase.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case for fetching articles.
 */
class FetchArticlesUseCase(
    private val repository: SpaceFlightRepository,
) {
    /**
     *  Fetches all articles with paging.
     */
    fun fetchArticles(): Flow<PagingData<Article>> =
        repository.getArticlesWithPaging().map {
            it.map { it.toArticle() }
        }
}
