package com.kurnavova.spacedout.data.spaceflights.network.mapper

import com.kurnavova.spacedout.data.spaceflights.network.model.ArticleResponse
import com.kurnavova.spacedout.domain.model.ArticleDetail

/**
 * Converts API response to domain model.
 */
internal fun ArticleResponse.toDomain(): ArticleDetail {
    return ArticleDetail(
        id = id,
        title = title,
        summary = summary,
        imageUrl = imageUrl,
        url = url,
        authors = authors.map { it.name }, // Extracts only author names
        publishedAt = publishedAt,
        isFavourite = false
    )
}
