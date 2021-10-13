package com.sammy.data.di

import androidx.room.Room
import com.sammy.data.CurrencyDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

private val databaseModule: Module = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CurrencyDatabase::class.java,
            "currency-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

private val daoModule: Module = module {
    single { get<CurrencyDatabase>().ratesDao() }
}

val dataModules: List<Module> = listOf(
    databaseModule,
    daoModule
)