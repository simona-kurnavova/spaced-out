package com.kurnavova.spacedout.data.network.mapper

import com.kurnavova.spacedout.data.network.model.ArticleListResponse
import com.kurnavova.spacedout.domain.model.Article

/**
 * Converts API response list to domain model list.
 */
fun ArticleListResponse.toDomain(): List<Article> =
    results.map { it.toDomain() }
