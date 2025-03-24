package com.kurnavova.spacedout.domain.api

import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.Article

/**
 * Repository for fetching space flight news.
 */
interface SpaceFlightRepository {
    /**
     * Returns a flow of articles.
     */
    suspend fun getNews(): ApiResult<List<Article>>

    /**
     * Returns an article by its unique identifier.
     *
     * @param id The unique identifier of the article.
     */
    suspend fun getArticleById(id: Int): ApiResult<Article>
}
