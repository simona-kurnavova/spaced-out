package com.kurnavova.spacedout.features.newslist.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurnavova.spacedout.data.connectivity.NetworkStatusProvider
import com.kurnavova.spacedout.features.newslist.usecase.FetchArticlesUseCase
import com.kurnavova.spacedout.features.ui.ArticleUseCaseResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the NewsListScreen.
 */
class NewsListViewModel(
    private val fetchArticlesUseCase: FetchArticlesUseCase,
    networkStatusProvider: NetworkStatusProvider,
) : ViewModel() {
    private val _uiState: MutableStateFlow<NewsListUiState> =
        MutableStateFlow(NewsListUiState.Idle)

    /**
     * Flow of the current UI state.
     */
    val uiState: StateFlow<NewsListUiState> = _uiState

    init {
        updateArticles()

        // Refresh articles when network connection is restored
        networkStatusProvider.isConnected.onEach { isConnected ->
            if (isConnected && _uiState.value !is NewsListUiState.Loaded) {
                updateArticles()
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: NewsListAction) {
        when (action) {
            NewsListAction.RefreshNews -> updateArticles()
            is NewsListAction.ShowDetail -> Unit // Handled in UI
        }
    }

    private fun updateArticles() {
        if (_uiState.value is NewsListUiState.Loading) {
            return // Already loading
        }

        _uiState.update { NewsListUiState.Loading }

        viewModelScope.launch {
            val result = fetchArticlesUseCase.fetchAll()

            _uiState.update {
                when(result) {
                    is ArticleUseCaseResult.Error ->
                        NewsListUiState.Error(result.errorMessage)

                    is ArticleUseCaseResult.Success ->
                        NewsListUiState.Loaded(result.data)
                }
            }
        }
    }
}

@Stable
sealed interface NewsListUiState {
    object Idle : NewsListUiState
    object Loading : NewsListUiState
    data class Error(@StringRes val message: Int) : NewsListUiState
    data class Loaded(val articles: ImmutableList<Article>) : NewsListUiState
}

@Immutable
data class Article(
    val id: Int,
    val title: String,
    val summary: String,
)

sealed class NewsListAction {
    object RefreshNews : NewsListAction()
    data class ShowDetail(val id: Int) : NewsListAction()
}
