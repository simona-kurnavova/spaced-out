package com.kurnavova.spacedout.domain.di

import com.kurnavova.spacedout.domain.usecase.FetchArticleDetailUseCase
import com.kurnavova.spacedout.domain.usecase.FetchArticlesUseCase
import com.kurnavova.spacedout.domain.usecase.ScheduleCleanupUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    // UseCases
    factoryOf(::FetchArticlesUseCase)
    factoryOf(::FetchArticleDetailUseCase)
    factoryOf(::ScheduleCleanupUseCase)
}
