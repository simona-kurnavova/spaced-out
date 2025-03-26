package com.kurnavova.spacedout.domain.usecase.mapper

import com.kurnavova.spacedout.domain.model.ArticleDetail
import com.kurnavova.spacedout.domain.usecase.model.Article
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
 * Maps an [ArticleDetail] to an [Article].
 */
internal fun ArticleDetail.toArticle(): Article = Article(
    id = id,
    title = title,
    summary = summary.trim(),
    imageUrl = imageUrl,
    url = url,
    authors = authors.joinToString(", "), // For simplicity, let's do this here (should be done in ui layer thought)
    publishedAt = formatDateTime(publishedAt),
    newsSite = newsSite
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
