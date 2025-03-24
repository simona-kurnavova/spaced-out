package com.kurnavova.spacedout.data.network.mapper

import com.kurnavova.spacedout.data.network.model.ArticleListItem
import com.kurnavova.spacedout.data.network.model.ArticleListResponse
import com.kurnavova.spacedout.domain.model.ArticleItem

/**
 * Converts API response list to domain model list.
 */
fun ArticleListResponse.toDomain(): List<ArticleItem> =
    results.map { it.toDomain() }

/**
 * Converts API response to domain model.
 */
private fun ArticleListItem.toDomain(): ArticleItem {
    return ArticleItem(
        id = id,
        title = title,
        summary = summary,
    )
}
