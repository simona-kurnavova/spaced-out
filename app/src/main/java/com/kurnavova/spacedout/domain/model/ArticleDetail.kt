package com.kurnavova.spacedout.domain.model

/**
 * Represents an article with all available details.
 *
 * @property id The unique identifier of the article.
 * @property title The title of the article.
 * @property summary The summary of the article.
 * @property imageUrl The URL of the image associated with the article.
 * @property url The URL of the article.
 * @property authors The authors of the article.
 * @property publishedAt The date the article was published.
 * @property isFavourite Whether the article is marked as favourite.
 */
data class ArticleDetail(
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String,
    val url: String,
    val authors: List<String>,
    val publishedAt: String,
    val isFavourite: Boolean,
)
