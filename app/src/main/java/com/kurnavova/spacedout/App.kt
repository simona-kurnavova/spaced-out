package com.kurnavova.spacedout

import android.app.Application
import com.kurnavova.spacedout.data.di.dataModule
import com.kurnavova.spacedout.domain.di.domainModule
import com.kurnavova.spacedout.domain.usecase.ScheduleCleanupUseCase
import com.kurnavova.spacedout.features.newsdetail.di.newsDetailModule
import com.kurnavova.spacedout.features.newslist.di.newsListModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Application class.
 */
class App : Application(){
    override fun onCreate() {
        super.onCreate()

        setupDependencyInjection()
    }

    private fun setupDependencyInjection() {
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.DEBUG)
            }
            androidContext(this@App)
            modules(dataModule, domainModule, newsDetailModule, newsListModule)

            scheduleCleanup()
        }
    }

    private fun KoinApplication.scheduleCleanup() {
        // Schedule a worker to clean up old data
        get<ScheduleCleanupUseCase>().scheduleCleanUp()
    }
}
