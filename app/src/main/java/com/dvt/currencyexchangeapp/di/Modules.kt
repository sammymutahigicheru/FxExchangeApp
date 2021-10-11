package com.dvt.currencyexchangeapp.di

import com.dvt.currencyexchangeapp.ui.conversion.repository.CurrencyExchangeRatesRepository
import com.dvt.currencyexchangeapp.ui.conversion.repository.ICurrencyExchangeRatesRepository
import com.dvt.currencyexchangeapp.ui.conversion.viewmodel.CurrencyExchangeRateViewModel
import com.dvt.currencyexchangeapp.ui.currencies.repository.CurrencyRepository
import com.dvt.currencyexchangeapp.ui.currencies.repository.CurrencyRespositoryImpl
import com.dvt.currencyexchangeapp.ui.currencies.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val repositoryModule: Module = module {
    single<CurrencyRepository> { CurrencyRespositoryImpl(get()) }
    single<ICurrencyExchangeRatesRepository> { CurrencyExchangeRatesRepository(get()) }
}

private val viewModelModule: Module = module {
    viewModel { CurrencyViewModel(get()) }
    viewModel { CurrencyExchangeRateViewModel(get()) }
}


val appModules: List<Module> = listOf(
    repositoryModule,
    viewModelModule
)