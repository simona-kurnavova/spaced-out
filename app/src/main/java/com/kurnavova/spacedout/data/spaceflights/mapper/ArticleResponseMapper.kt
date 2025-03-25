package com.kurnavova.spacedout.data.spaceflights.mapper

import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity
import com.kurnavova.spacedout.data.spaceflights.network.model.ArticleResponse

/**
 * Maps [ArticleResponse] to [ArticleEntity].
 */
fun ArticleResponse.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        url = url,
        summary = summary,
        author = authors.joinToString(", ") { it.name },
        date = publishedAt
    )
}
