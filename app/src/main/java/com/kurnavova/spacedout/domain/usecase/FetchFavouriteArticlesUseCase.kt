package com.kurnavova.spacedout.domain.usecase

import androidx.paging.PagingData
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.model.ArticleDetail
import kotlinx.coroutines.flow.Flow

/**
 * Use case for fetching paginated favourite articles.
 */
class FetchFavouriteArticlesUseCase(
    private val repository:
    SpaceFlightRepository,
) {
    /**
     * Fetches all favourite articles with paging.
     */
    fun invoke(): Flow<PagingData<ArticleDetail>> =
        repository.getFavouriteArticlesWithPaging()
}
