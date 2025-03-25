package com.kurnavova.spacedout.data.spaceflights.network

import com.kurnavova.spacedout.data.spaceflights.network.model.ArticleListResponse
import com.kurnavova.spacedout.data.spaceflights.network.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

/**
 * Represents a Retrofit API for space flight article data.
 */
interface SpaceFlightApi {
    /**
     * Fetches a paged list of articles.
     *
     * @param offset The initial index from which to return the results.
     * @param limit The maximum number of articles to return per page.
     */
    @GET("articles/")
    suspend fun getPagedArticles(@Query("offset") offset: Int, @Query("limit") limit: Int): Response<ArticleListResponse>

    /**
     * Fetches a single article by its unique identifier.
     *
     * @param id The unique identifier of the article.
     */
    @GET("articles/{id}/")
    suspend fun getArticle(@Path("id") id: Int): Response<ArticleResponse>
}
