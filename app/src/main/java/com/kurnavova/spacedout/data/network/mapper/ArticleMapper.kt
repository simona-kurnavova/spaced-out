package com.kurnavova.spacedout.data.network.mapper

import com.kurnavova.spacedout.data.network.model.ArticleResponse
import com.kurnavova.spacedout.domain.model.Article

/**
 * Converts API response to domain model.
 */
fun ArticleResponse.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        url = url,
        summary = summary
    )
}
