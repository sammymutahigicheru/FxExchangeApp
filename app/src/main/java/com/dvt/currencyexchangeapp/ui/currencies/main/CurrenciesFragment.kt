package com.dvt.currencyexchangeapp.ui.currencies.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dvt.core.Constants
import com.dvt.currencyexchangeapp.databinding.FragmentCurrenciesBinding
import com.dvt.currencyexchangeapp.ui.currencies.viewmodel.CurrencyViewModel
import com.dvt.currencyexchangeapp.utils.ResponseState
import com.dvt.network.models.Currencies
import com.dvt.network.models.CurrencyResponse
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class CurrenciesFragment : Fragment() {

    private val binding: FragmentCurrenciesBinding by lazy {
        FragmentCurrenciesBinding.inflate(layoutInflater)
    }

    private val viewModel: CurrencyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // val countryCurrency = getSymbol("KE")
     //   Timber.e("Country Currency: $countryCurrency")

        getCurrencies()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.currencies.collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        Timber.e("***Loading***")
                    }
                    is ResponseState.Result<*> -> {
                        val response:CurrencyResponse = state.data as CurrencyResponse
                        setUpRecyclerView(response)
                    }
                    is ResponseState.Error -> {
                        Timber.e("Error: ${state.message}")
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(response: CurrencyResponse) {
        val arrayList = arrayListOf<Currencies>()
        arrayList.add(response.currencies)
        val currencyAdapter = CurrencyAdapter()
        binding.currencyRecycler.apply {
            adapter = currencyAdapter
            hasFixedSize()
        }
        currencyAdapter.submitList(arrayList)
    }

    private fun getCurrencies() {
        viewModel.getCurrencies(Constants.API_KEY)
    }


}