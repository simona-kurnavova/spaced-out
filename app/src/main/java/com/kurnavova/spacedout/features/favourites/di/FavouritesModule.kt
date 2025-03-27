package com.kurnavova.spacedout.features.favourites.di

import com.kurnavova.spacedout.features.favourites.ui.FavouriteArticlesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Module for the favourites feature.
 */
val favouritesModule = module {
    viewModelOf(::FavouriteArticlesViewModel)
}