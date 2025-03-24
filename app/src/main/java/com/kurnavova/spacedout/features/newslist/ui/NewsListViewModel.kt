package com.kurnavova.spacedout.features.newslist.ui

import androidx.annotation.StringRes
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
    private val _uiState: MutableStateFlow<NewsListUiState> =
        MutableStateFlow(NewsListUiState.Idle)

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
        _uiState.update { NewsListUiState.Loading }

        viewModelScope.launch {
            val state = fetchArticlesUseCase.fetchAll()

            _uiState.update {
                if (state.errorMessage != null) {
                    NewsListUiState.Error(state.errorMessage)
                } else {
                    NewsListUiState.Loaded(state.articles)
                }
            }
        }
    }
}

sealed interface NewsListUiState {
    object Idle : NewsListUiState
    object Loading : NewsListUiState
    data class Error(@StringRes val message: Int) : NewsListUiState
    data class Loaded(val articles: ImmutableList<Article>) : NewsListUiState
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
