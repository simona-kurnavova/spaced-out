package com.kurnavova.spacedout.domain.model

/**
 * Represents an article.
 *
 * @property id The unique identifier of the article.
 * @property title The title of the article.
 * @property url The URL of the article.
 * @property summary The summary of the article.
 */
data class Article(
    val id: Int,
    val title: String,
    val url: String,
    val summary: String,
)
