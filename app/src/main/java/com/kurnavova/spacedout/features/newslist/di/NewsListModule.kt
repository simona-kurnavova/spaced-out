package com.kurnavova.spacedout.features.newslist.di

import com.kurnavova.spacedout.features.newslist.ui.NewsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val newsListModule = module {
    // ViewModel
    viewModelOf(::NewsListViewModel)
}
