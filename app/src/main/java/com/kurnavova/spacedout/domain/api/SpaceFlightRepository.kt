package com.kurnavova.spacedout.domain.api

import androidx.paging.PagingData
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.ArticleDetail
import kotlinx.coroutines.flow.Flow

/**
 * Repository for fetching space flight news.
 */
interface SpaceFlightRepository {

    /**
     * Returns a flow of articles as paging data.
     */
    fun getArticlesWithPaging(): Flow<PagingData<ArticleDetail>>

    /**
     * Returns an article by its unique identifier.
     *
     * @param id The unique identifier of the article.
     */
    suspend fun getArticleById(id: Int): ApiResult<ArticleDetail>
}
