package com.kurnavova.spacedout.domain.usecase

import androidx.paging.PagingData
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.model.ArticleDetail
import kotlinx.coroutines.flow.Flow

/**
 * Use case for fetching articles.
 */
class FetchArticlesUseCase(
    private val repository: SpaceFlightRepository,
) {
    /**
     *  Fetches all articles with paging.
     */
    fun invoke(): Flow<PagingData<ArticleDetail>> =
        repository.getArticlesWithPaging()
}
