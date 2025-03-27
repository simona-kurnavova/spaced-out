package com.kurnavova.spacedout.domain.di

import com.kurnavova.spacedout.domain.usecase.FetchArticleDetailUseCase
import com.kurnavova.spacedout.domain.usecase.FetchArticlesUseCase
import com.kurnavova.spacedout.domain.usecase.ScheduleCleanupUseCase
import com.kurnavova.spacedout.domain.usecase.UpdateFavouriteStatusUseCase
import com.kurnavova.spacedout.domain.usecase.FetchFavouriteArticlesUseCase

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * Module for domain layer.
 */
internal val domainModule = module {
    // UseCases
    factoryOf(::FetchArticlesUseCase)
    factoryOf(::FetchArticleDetailUseCase)
    factoryOf(::ScheduleCleanupUseCase)
    factoryOf(::UpdateFavouriteStatusUseCase)
    factoryOf(::FetchFavouriteArticlesUseCase)
}
