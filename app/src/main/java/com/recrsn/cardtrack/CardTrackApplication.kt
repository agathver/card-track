package com.recrsn.cardtrack

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CardTrackApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CardTrackApplication)
            modules(appModule(this@CardTrackApplication))
        }
    }
}
