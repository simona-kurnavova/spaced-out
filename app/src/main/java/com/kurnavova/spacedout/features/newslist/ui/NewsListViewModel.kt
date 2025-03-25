package com.kurnavova.spacedout.features.newslist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kurnavova.spacedout.domain.usecase.FetchArticlesUseCase
import com.kurnavova.spacedout.domain.usecase.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel for the NewsListScreen.
 */
class NewsListViewModel(
    fetchArticlesUseCase: FetchArticlesUseCase,
) : ViewModel() {

    /**
     * Flow of the current UI state.
     */
    val uiState: Flow<PagingData<Article>> = fetchArticlesUseCase
        .fetchArticles()
        .cachedIn(viewModelScope)
}

sealed class NewsListAction {
    data class ShowDetail(val id: Int) : NewsListAction()
}
