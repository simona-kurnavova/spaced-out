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

/**
 * Represents an action that can be performed on the NewsListScreen.
 */
sealed class NewsListAction {
    /**
     * Show the detail screen of an article.
     *
     * @param id The ID of the article to show.
     */
    data class ShowDetail(val id: Int) : NewsListAction()
}
