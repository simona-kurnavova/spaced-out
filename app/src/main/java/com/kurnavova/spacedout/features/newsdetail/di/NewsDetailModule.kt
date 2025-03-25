package com.kurnavova.spacedout.features.newsdetail.di

import com.kurnavova.spacedout.features.newsdetail.ui.NewsDetailViewModel
import com.kurnavova.spacedout.domain.usecase.FetchArticleDetailUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val newsDetailModule = module {
    // ViewModel
    viewModelOf(::NewsDetailViewModel)

    // UseCase
    factoryOf(::FetchArticleDetailUseCase)
}
