package com.kurnavova.spacedout.data.network.di

import com.kurnavova.spacedout.data.network.SpaceFlightDao
import com.kurnavova.spacedout.data.network.SpaceFlightDaoInitializer
import com.kurnavova.spacedout.data.network.SpaceFlightRepositoryImpl
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataModule = module {
    // Data Access Object
    single<SpaceFlightDao> { SpaceFlightDaoInitializer.create() }

    // Repository implementation
    factoryOf(::SpaceFlightRepositoryImpl) bind SpaceFlightRepository::class
}
