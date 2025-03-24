package com.kurnavova.spacedout.data.connectivity.di

import com.kurnavova.spacedout.data.connectivity.NetworkStatusProvider
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val connectivityModule = module {
    // Connectivity checker
    singleOf(::NetworkStatusProvider)
}