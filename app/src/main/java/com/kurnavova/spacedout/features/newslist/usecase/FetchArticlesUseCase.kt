package com.kurnavova.spacedout.features.newslist.usecase

import androidx.annotation.StringRes
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.model.ErrorType
import com.kurnavova.spacedout.features.newslist.ui.Article
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

/**
 * Use case for fetching articles.
 */
class FetchArticlesUseCase(
    private val repository: SpaceFlightRepository
) {
    /**
     *  Fetches all articles.
     */
    suspend fun fetchAll(): ArticlesState {
        val result = repository.getNews()

        return when(result) {
            is ApiResult.Success -> {
                ArticlesState(
                    articles = result.data.map {
                        Article(
                            id = it.id,
                            title = it.title,
                            summary = it.summary,
                        )
                    }.toPersistentList()
                )
            }
            is ApiResult.Error -> {
                ArticlesState(
                    errorMessage = when(result.type) {
                        ErrorType.Server -> R.string.error_server
                        ErrorType.Client -> R.string.error_client
                        ErrorType.Network -> R.string.error_network
                        ErrorType.Unknown -> R.string.error_unknown
                    }
                )
            }
        }
    }
}

data class ArticlesState(
    @StringRes
    val errorMessage: Int? = null,
    val articles: ImmutableList<Article> = persistentListOf()
)
