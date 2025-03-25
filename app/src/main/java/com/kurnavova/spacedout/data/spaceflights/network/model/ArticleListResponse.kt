package com.kurnavova.spacedout.data.spaceflights.network.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a list of articles from API response.
 *
 * @property previous The URL of the previous page.
 * @property next The URL of the next page.
 * @property results List of articles.
 */
data class ArticleListResponse(
    @SerializedName("previous")
    val previous: String?,

    @SerializedName("next")
    val next: String?,

    @SerializedName("results")
    val results: List<ArticleResponse>,
)
