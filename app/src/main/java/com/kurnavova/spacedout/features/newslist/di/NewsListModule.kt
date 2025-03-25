package com.kurnavova.spacedout.features.newslist.di

import com.kurnavova.spacedout.features.newslist.ui.NewsListViewModel
import com.kurnavova.spacedout.domain.usecase.FetchArticlesUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val newsListModule = module {
    // ViewModel
    viewModelOf(::NewsListViewModel)

    // UseCase
    factoryOf(::FetchArticlesUseCase)
}
