package com.kurnavova.spacedout.data.spaceflights.mapper

import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity
import com.kurnavova.spacedout.data.spaceflights.network.model.ArticleResponse

/**
 * Maps [ArticleResponse] to [ArticleEntity] (from remote to local).
 */
internal fun ArticleResponse.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        url = url,
        summary = summary,
        author = authors.map { it.name },
        date = publishedAt,
        newsSite = newsSite
    )
}
