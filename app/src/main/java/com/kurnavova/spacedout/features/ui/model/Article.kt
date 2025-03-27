package com.kurnavova.spacedout.features.ui.model

/**
 * Represents an article.
 *
 * @property id The unique identifier.
 * @property title The title of the article.
 * @property summary The summary of the article.
 * @property imageUrl The URL of the image.
 * @property url The URL of the article.
 * @property authors The authors of the article.
 * @property publishedAt The date when the article was published.
 */
data class Article(
    val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String,
    val url: String,
    val authors: String,
    val publishedAt: String,
)
