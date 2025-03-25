package com.kurnavova.spacedout.data.network.mapper

import com.kurnavova.spacedout.data.network.model.ArticleListResponse
import com.kurnavova.spacedout.domain.model.ArticleDetail

/**
 * Converts API response list to domain model list.
 */
fun ArticleListResponse.toDomain(): List<ArticleDetail> =
    results.map { it.toDomain() }
