package com.kurnavova.spacedout.data.spaceflights.network.mapper

import com.kurnavova.spacedout.data.spaceflights.network.model.ArticleListResponse
import com.kurnavova.spacedout.domain.model.ArticleDetail

/**
 * Converts API response list to domain model list.
 */
internal fun ArticleListResponse.toDomain(): List<ArticleDetail> =
    results.map { it.toDomain() }
