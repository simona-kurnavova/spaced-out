package com.kurnavova.spacedout.features.newsdetail.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurnavova.spacedout.R
import com.kurnavova.spacedout.features.newsdetail.usecase.ArticleUiDetail
import com.kurnavova.spacedout.features.newsdetail.usecase.FetchArticleDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val fetchArticleDetailUseCase: FetchArticleDetailUseCase
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
            is NewsDetailAction.FetchArticle -> fetchArticle(action.id) // Handled in UI
        }
    }

    private fun fetchArticle(id: Int) {
        if (currentId == id) {
            return // Already loaded
        }

        _uiState.update { NewsDetailUiState.Loading }
       currentId = id

       viewModelScope.launch {
           val result = fetchArticleDetailUseCase.fetchArticle(id)
           _uiState.update {
               when {
                   result.errorMessage != null -> NewsDetailUiState.Error(result.errorMessage)
                   result.article != null -> NewsDetailUiState.Loaded(result.article)
                   else -> NewsDetailUiState.Error(R.string.error_unknown)
               }
           }
       }
    }
}

sealed interface NewsDetailUiState {
    object Idle : NewsDetailUiState
    object Loading : NewsDetailUiState
    data class Error(@StringRes val message: Int) : NewsDetailUiState
    data class Loaded(val article: ArticleUiDetail) : NewsDetailUiState
}

sealed class NewsDetailAction {
    data class FetchArticle(val id: Int) : NewsDetailAction()
}
