package com.kurnavova.spacedout.features.newsdetail.usecase

import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.data.connectivity.NetworkStatusProvider
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.features.mapper.toErrorMessage
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
 * Use case for fetching an article by its unique identifier.
 */
class FetchArticleDetailUseCase(
    private val repository: SpaceFlightRepository,
    private val networkStatusProvider: NetworkStatusProvider,
) {
    /**
     * Fetches an article by its unique identifier.
     *
     * @param id The unique identifier of the article.
     */
    suspend fun fetchArticle(id: Int): ArticleState {
        if (!networkStatusProvider.hasInternet()) {
            return ArticleState(errorMessage = R.string.error_network)
        }

        val result = repository.getArticleById(id)

        return when(result) {
            is ApiResult.Success -> {
                val article = result.data
                ArticleState(
                    article = ArticleUiDetail(
                        title = article.title,
                        summary = article.summary.trim(),
                        imageUrl = article.imageUrl,
                        url = article.url,
                        authors = article.authors.joinToString(","),
                        publishedAt = formatDateTime(article.publishedAt)
                    )
                )
            }

            is ApiResult.Error -> {
                ArticleState(errorMessage = result.toErrorMessage())
            }
        }
    }

    fun formatDateTime(isoDateTime: String, locale: Locale = Locale.getDefault()): String {
        val instant = Instant.parse(isoDateTime)
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withLocale(locale)
            .withZone(ZoneId.systemDefault())

        return formatter.format(instant)
    }
}

data class ArticleState(
    val errorMessage: Int? = null,
    val article: ArticleUiDetail? = null
)

data class ArticleUiDetail(
    val title: String,
    val summary: String,
    val imageUrl: String,
    val url: String,
    val authors: String,
    val publishedAt: String,
)
