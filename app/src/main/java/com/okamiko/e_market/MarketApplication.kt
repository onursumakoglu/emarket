package com.okamiko.e_market

import android.app.Application
import com.okamiko.core.di.dbModule
import com.okamiko.core.di.networkModule
import com.okamiko.feature.di.featureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MarketApplication)
            modules(networkModule, dbModule, featureModule)
        }
    }
}