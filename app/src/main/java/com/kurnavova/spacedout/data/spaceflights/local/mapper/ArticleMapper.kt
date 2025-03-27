package com.kurnavova.spacedout.data.spaceflights.local.mapper

import com.kurnavova.spacedout.data.spaceflights.local.model.ArticleEntity
import com.kurnavova.spacedout.domain.model.ArticleDetail

/**
 * Converts a database entity to a domain model.
 */
internal fun ArticleEntity.toDomain(): ArticleDetail {
    return ArticleDetail(
        id = id,
        title = title,
        summary = summary,
        imageUrl = imageUrl,
        url = url,
        authors = author,
        publishedAt = date,
        isFavourite = isFavourite
    )
}
