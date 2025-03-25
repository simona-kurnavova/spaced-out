package com.kurnavova.spacedout.features.newsdetail.ui.mapper

import com.kurnavova.spacedout.domain.model.ArticleDetail
import com.kurnavova.spacedout.features.newsdetail.ui.ArticleUiDetail
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
 * Maps an [ArticleDetail] to an [ArticleUiDetail].
 */
fun ArticleDetail.toArticleUiDetail(): ArticleUiDetail = ArticleUiDetail(
    title = title,
    summary = summary.trim(),
    imageUrl = imageUrl,
    url = url,
    authors = authors.joinToString(","),
    publishedAt = formatDateTime(publishedAt)
)

private fun formatDateTime(isoDateTime: String, locale: Locale = Locale.getDefault()): String {
    val instant = runCatching {
        Instant.parse(isoDateTime)
    }.getOrNull() ?: return isoDateTime // Return previous string in case parsing fails.

    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        .withLocale(locale)
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}