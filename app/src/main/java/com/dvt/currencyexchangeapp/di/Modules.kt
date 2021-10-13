package com.dvt.currencyexchangeapp.di

import com.dvt.currencyexchangeapp.ui.conversion.repository.CurrencyExchangeRatesRepository
import com.dvt.currencyexchangeapp.ui.conversion.repository.ICurrencyExchangeRatesRepository
import com.dvt.currencyexchangeapp.ui.conversion.viewmodel.CurrencyExchangeRateViewModel
import com.dvt.currencyexchangeapp.ui.histories.HistoriesRepository
import com.dvt.currencyexchangeapp.ui.histories.HistoryViewModel
import com.dvt.currencyexchangeapp.ui.histories.IHistoriesRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val repositoryModule: Module = module {
    single<ICurrencyExchangeRatesRepository> { CurrencyExchangeRatesRepository(get()) }
    single<IHistoriesRepository> { HistoriesRepository(get(),get()) }
}

private val viewModelModule: Module = module {
    viewModel { CurrencyExchangeRateViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}


val appModules: List<Module> = listOf(
    repositoryModule,
    viewModelModule
)