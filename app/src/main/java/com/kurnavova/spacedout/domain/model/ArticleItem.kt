package com.kurnavova.spacedout.domain.model

/**
 * Represents compressed article data for list display.
 *
 * @property id The unique identifier of the article.
 * @property title The title of the article.
 * @property summary The summary of the article.
 */
data class ArticleItem(
    val id: Int,
    val title: String,
    val summary: String,
)
