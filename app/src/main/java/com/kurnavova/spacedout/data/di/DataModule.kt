package com.kurnavova.spacedout.data.di

import com.kurnavova.spacedout.data.spaceflights.CleanupManagerImpl
import com.kurnavova.spacedout.data.spaceflights.SpaceFlightRepositoryImpl
import com.kurnavova.spacedout.data.spaceflights.local.ArticleDao
import com.kurnavova.spacedout.data.spaceflights.local.ArticleDatabase
import com.kurnavova.spacedout.data.spaceflights.network.SpaceFlightApi
import com.kurnavova.spacedout.data.spaceflights.network.SpaceFlightApiInitializer
import com.kurnavova.spacedout.domain.api.CleanupManager
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Module for data layer dependencies.
 */
val dataModule = module {
    // Remote api
    single<SpaceFlightApi> { SpaceFlightApiInitializer.create() }

    // Local database & Dao
    single { ArticleDatabase.create(androidContext()) }
    factory<ArticleDao> { get<ArticleDatabase>().articleDao }

    // Cleanup
    factoryOf(::CleanupManagerImpl) bind CleanupManager::class

    // Repository implementation
    factoryOf(::SpaceFlightRepositoryImpl) bind SpaceFlightRepository::class
}
