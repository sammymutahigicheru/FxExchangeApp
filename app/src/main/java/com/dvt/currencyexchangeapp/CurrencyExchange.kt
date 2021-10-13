package com.dvt.currencyexchangeapp

import android.app.Application
import com.dvt.currencyexchangeapp.di.appModules
import com.dvt.network.di.networkModules
import com.sammy.data.di.dataModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.logger.Level
import org.koin.core.module.Module
import timber.log.Timber

class CurrencyExchange : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initKoin()
    }

    private fun initKoin() {
        try {
            startKoin {
                androidLogger(Level.ERROR)
                androidContext(applicationContext)
                val modules = mutableListOf<Module>().apply {
                    addAll(networkModules)
                    addAll(appModules)
                    addAll(dataModules)
                }
                modules(modules)
            }
        } catch (error: KoinAppAlreadyStartedException) {
            //log the error
        }
    }
}