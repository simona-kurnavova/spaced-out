package com.kurnavova.spacedout.domain.usecase.model

/**
 * Represents an article.
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
