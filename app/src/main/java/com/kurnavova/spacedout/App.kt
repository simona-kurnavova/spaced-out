package com.kurnavova.spacedout

import android.app.Application
import com.kurnavova.spacedout.data.network.di.dataModule
import com.kurnavova.spacedout.features.newsdetail.di.newsDetailModule
import com.kurnavova.spacedout.features.newslist.di.newsListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Application class.
 */
class App : Application(){
    override fun onCreate() {
        super.onCreate()

        // Setup DI
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.DEBUG)
            }
            androidContext(this@App)
            modules(dataModule, newsDetailModule, newsListModule)
        }
    }
}
