package com.kurnavova.spacedout.data.network

import com.kurnavova.spacedout.data.network.model.ArticleListResponse
import com.kurnavova.spacedout.data.network.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

/**
 * Represents a Retrofit API for space flight article data.
 */
interface SpaceFlightApi {
    @GET("articles/")
    suspend fun getArticles(@Query("limit") limit: Int = LIMIT): Response<ArticleListResponse>

    @GET("articles/{id}/")
    suspend fun getArticle(@Path("id") id: Int): Response<ArticleResponse>
}

private const val LIMIT = 100
