package com.kurnavova.spacedout.features.newslist.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurnavova.spacedout.features.newslist.usecase.FetchArticlesUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the NewsListScreen.
 */
class NewsListViewModel(
    private val fetchArticlesUseCase: FetchArticlesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewsListUiState())

    /**
     * Flow of the current UI state.
     */
    val uiState: StateFlow<NewsListUiState> = _uiState

    init {
        updateArticles()
    }

    fun onAction(action: NewsListAction) {
        when (action) {
            NewsListAction.RefreshNews -> updateArticles()
            is NewsListAction.ShowDetail -> Unit // Handled in UI
        }
    }

    private fun updateArticles() {
        _uiState.update { it.copy(loadingState = LoadingState.Loading) }

        viewModelScope.launch {
            val state = fetchArticlesUseCase.fetchAll()

            _uiState.update {
                if (state.errorMessage != null) {
                    NewsListUiState(LoadingState.Error(state.errorMessage))
                } else {
                    NewsListUiState(LoadingState.Loaded(state.articles))
                }
            }
        }
    }
}

@Stable
data class NewsListUiState(
    val loadingState: LoadingState = LoadingState.Idle,
)

sealed interface LoadingState {
    object Idle : LoadingState
    object Loading : LoadingState
    data class Error(@StringRes val message: Int) : LoadingState
    data class Loaded(val articles: ImmutableList<Article>) : LoadingState
}

data class Article(
    val id: Int,
    val title: String,
    val summary: String,
)

sealed class NewsListAction {
    object RefreshNews : NewsListAction()
    data class ShowDetail(val id: Int) : NewsListAction()
}
