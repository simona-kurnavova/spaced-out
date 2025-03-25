package com.kurnavova.spacedout.data.spaceflights.network.model

import com.google.gson.annotations.SerializedName

/**
 * Represents an article from API response.
 *
 * @property id The unique identifier of the article.
 * @property title The title of the article.
 * @property url The URL of the article.
 * @property summary The summary of the article.
 * @property imageUrl The URL of the article image.
 * @property authors The list of authors of the article.
 * @property publishedAt The date when the article was published.
 */
data class ArticleResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("summary")
    val summary: String,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("authors")
    val authors: List<Author>,

    @SerializedName("published_at")
    val publishedAt: String
)

/**
 * Represents an author of an article.
 *
 * @property name The name of the author.
 */
data class Author(
    @SerializedName("name")
    val name: String
)
