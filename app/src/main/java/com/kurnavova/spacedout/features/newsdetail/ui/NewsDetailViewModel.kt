package com.kurnavova.spacedout.features.newsdetail.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurnavova.spacedout.domain.model.ApiResult
import com.kurnavova.spacedout.domain.usecase.FetchArticleDetailUseCase
import com.kurnavova.spacedout.features.ui.mapper.toArticle
import com.kurnavova.spacedout.features.ui.model.Article
import com.kurnavova.spacedout.ui.mapper.toErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * View model for the news detail screen.
 */
class NewsDetailViewModel(
    private val fetchArticleDetailUseCase: FetchArticleDetailUseCase,
) : ViewModel() {
    private var currentId: Int? = null

    private val _uiState: MutableStateFlow<NewsDetailUiState> = MutableStateFlow(NewsDetailUiState.Idle)

    /**
     * Flow of the current UI state.
     */
    val uiState: StateFlow<NewsDetailUiState> = _uiState

    /**
     * Handles an action.
     */
    fun onAction(action: NewsDetailAction) {
        when (action) {
            is NewsDetailAction.FetchArticle -> fetchArticle(action.id)
        }
    }

    private fun fetchArticle(id: Int) {
        if (_uiState.value is NewsDetailUiState.Loading) {
            return // Already loading
        }

        if (currentId == id && _uiState.value is NewsDetailUiState.Loaded) {
            return // Already loaded
        }

        _uiState.update { NewsDetailUiState.Loading }
        currentId = id

        viewModelScope.launch {
            val result = fetchArticleDetailUseCase.invoke(id)

            _uiState.update {
                when (result) {
                    is ApiResult.Error ->
                        NewsDetailUiState.Error(result.toErrorMessage())

                    is ApiResult.Success -> {
                        Log.d("NewsDetailViewModel", "ArticleUseCaseResult.Success ${result.data}")
                        NewsDetailUiState.Loaded(result.data.toArticle())

                    }
                }
           }
       }
    }
}

/**
 * Represents the UI state of the detail screen.
 */
@Stable
sealed interface NewsDetailUiState {
    /**
     * Represents the idle/default state.
     */
    object Idle : NewsDetailUiState

    /**
     * Represents the loading state.
     */
    object Loading : NewsDetailUiState

    /**
     * Represents an error state.
     *
     * @param message The error message string resource.
     */
    data class Error(@StringRes val message: Int) : NewsDetailUiState

    /**
     * Represents the loaded state.
     *
     * @param article The loaded article.
     */
    data class Loaded(val article: Article) : NewsDetailUiState
}

/**
 * Represents an action that can be performed on the news detail screen.
 */
sealed class NewsDetailAction {
    /**
     * Fetches the article with the given ID.
     *
     * @param id The ID of the article to fetch.
     */
    data class FetchArticle(val id: Int) : NewsDetailAction()
}
