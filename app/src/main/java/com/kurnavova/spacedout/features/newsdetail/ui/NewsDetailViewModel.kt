package com.kurnavova.spacedout.features.newsdetail.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurnavova.spacedout.data.connectivity.NetworkStatusProvider
import com.kurnavova.spacedout.features.newsdetail.usecase.FetchArticleDetailUseCase
import com.kurnavova.spacedout.features.ui.ArticleUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val fetchArticleDetailUseCase: FetchArticleDetailUseCase,
    networkStatusProvider: NetworkStatusProvider,
) : ViewModel() {
    private var currentId: Int? = null

    private val _uiState: MutableStateFlow<NewsDetailUiState> = MutableStateFlow(NewsDetailUiState.Idle)

    /**
     * Flow of the current UI state.
     */
    val uiState: StateFlow<NewsDetailUiState> = _uiState

    init {
        // Recovery after offline mode
        networkStatusProvider.isConnected.onEach { isConnected ->
            if (isConnected && _uiState.value !is NewsDetailUiState.Loaded) {
                currentId?.let { fetchArticle(it) }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Handles an action.
     */
    fun onAction(action: NewsDetailAction) {
        when (action) {
            is NewsDetailAction.FetchArticle -> fetchArticle(action.id) // Handled in UI
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
            val result = fetchArticleDetailUseCase.fetchArticle(id)

            _uiState.update {
                when (result) {
                    is ArticleUseCaseResult.Error ->
                        NewsDetailUiState.Error(result.errorMessage)

                    is ArticleUseCaseResult.Success ->
                        NewsDetailUiState.Loaded(result.data)
                }
           }
       }
    }
}

@Stable
sealed interface NewsDetailUiState {
    object Idle : NewsDetailUiState
    object Loading : NewsDetailUiState
    data class Error(@StringRes val message: Int) : NewsDetailUiState
    data class Loaded(val article: ArticleUiDetail) : NewsDetailUiState
}

@Immutable
data class ArticleUiDetail(
    val title: String,
    val summary: String,
    val imageUrl: String,
    val url: String,
    val authors: String,
    val publishedAt: String,
)

sealed class NewsDetailAction {
    data class FetchArticle(val id: Int) : NewsDetailAction()
}
