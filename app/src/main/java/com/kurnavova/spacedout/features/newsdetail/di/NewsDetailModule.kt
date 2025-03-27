package com.kurnavova.spacedout.features.newsdetail.di

import com.kurnavova.spacedout.features.newsdetail.ui.NewsDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Module for news detail feature.
 */
val newsDetailModule = module {
    // ViewModel
    viewModelOf(::NewsDetailViewModel)
}
