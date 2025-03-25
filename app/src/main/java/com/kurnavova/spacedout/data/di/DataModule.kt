package com.kurnavova.spacedout.data.di

import com.kurnavova.spacedout.data.network.SpaceFlightApi
import com.kurnavova.spacedout.data.network.SpaceFlightApiInitializer
import com.kurnavova.spacedout.data.SpaceFlightRepositoryImpl
import com.kurnavova.spacedout.domain.api.SpaceFlightRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val dataModule = module {
    // Data Access Object
    single<SpaceFlightApi> { SpaceFlightApiInitializer.create() }

    // Repository implementation
    factoryOf(::SpaceFlightRepositoryImpl) bind SpaceFlightRepository::class
}
