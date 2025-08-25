package com.seno.gencidev

import android.app.Application
import com.seno.core.data.di.coreDataModule
import com.seno.home.data.di.homeDataModule
import com.seno.home.presentation.di.homePresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class GencidevApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@GencidevApplication)
            androidLogger()
            modules(
                coreDataModule,
                homePresentationModule,
                homeDataModule
            )
        }
    }
}