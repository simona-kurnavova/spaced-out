package com.kurnavova.spacedout.features.newslist.di

import com.kurnavova.spacedout.features.newslist.ui.NewsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Module for news list feature.
 */
val newsListModule = module {
    // ViewModel
    viewModelOf(::NewsListViewModel)
}
