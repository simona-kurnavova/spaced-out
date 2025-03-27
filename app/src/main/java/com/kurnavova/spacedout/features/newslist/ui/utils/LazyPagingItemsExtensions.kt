package com.kurnavova.spacedout.features.newslist.ui.utils

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.kurnavova.spacedout.domain.usecase.model.Article

/**
 * Returns true if the list is loading for the very first time.
 */
val LazyPagingItems<Article>.isInitialLoad: Boolean
    get() = loadState.refresh is LoadState.Loading && itemCount == 0

/**
 * Returns true if the list is refreshing (a.k.a. there are/were some data and we asked for refresh).
 */
val LazyPagingItems<Article>.isRefreshing: Boolean
    get() = loadState.refresh is LoadState.Loading && itemCount != 0

/**
 * Returns true if the list is loading more data.
 */
val LazyPagingItems<Article>.isAppendLoad: Boolean
    get() = loadState.append is LoadState.Loading && !isInitialLoad

/**
 * Returns true if there was some error with initial loading.
 */
val LazyPagingItems<Article>.isInitialLoadError: Boolean
    get() = loadState.refresh is LoadState.Error && itemCount == 0

/**
 * Returns true if there was some error with loading more data (excludes initial load error).
 */
val LazyPagingItems<Article>.isAppendError: Boolean
    get() = loadState.append is LoadState.Error && !isInitialLoadError

/**
 * Returns true if there was an error with initial loading, but we still have some data - a.k.a. offline mode
 */
val LazyPagingItems<Article>.isOfflineWithData: Boolean
    get() = loadState.refresh is LoadState.Error && itemCount > 0
