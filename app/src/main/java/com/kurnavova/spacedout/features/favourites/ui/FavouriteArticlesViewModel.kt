package com.kurnavova.spacedout.features.favourites.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kurnavova.spacedout.domain.usecase.FetchFavouriteArticlesUseCase
import com.kurnavova.spacedout.features.ui.mapper.toArticle
import com.kurnavova.spacedout.features.ui.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * ViewModel for the FavouriteArticlesScreen.
 */
class FavouriteArticlesViewModel(
    fetchFavouriteArticlesUseCase: FetchFavouriteArticlesUseCase
) : ViewModel() {

    /**
     * Flow of the current UI state.
     */
    val uiState: Flow<PagingData<Article>> = fetchFavouriteArticlesUseCase
        .invoke().map {
            it.map { articleDetail -> articleDetail.toArticle() }
        }
        .cachedIn(viewModelScope)
}

/**
 * Represents an action that can be performed on the FavouriteArticlesScreen.
 */
sealed class FavouriteAction {
    /**
     * Show the detail screen of an article.
     *
     * @param id The ID of the article to show.
     */
    data class ShowDetail(val id: Int) : FavouriteAction()
}
