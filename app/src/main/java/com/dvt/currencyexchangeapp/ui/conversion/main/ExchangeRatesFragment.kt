package com.dvt.currencyexchangeapp.ui.conversion.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dvt.core.Constants
import com.dvt.core.helpers.getAllCountries
import com.dvt.core.helpers.getCountryCode
import com.dvt.core.helpers.getCountryCurrency
import com.dvt.currencyexchangeapp.R
import com.dvt.currencyexchangeapp.databinding.FragmentExchangeRatesBinding
import com.dvt.currencyexchangeapp.ui.conversion.viewmodel.CurrencyExchangeRateViewModel
import com.dvt.currencyexchangeapp.utils.ResponseState
import com.dvt.network.models.convert.ConversionResponse
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ExchangeRatesFragment : Fragment() {

    private val binding: FragmentExchangeRatesBinding by lazy {
        FragmentExchangeRatesBinding.inflate(layoutInflater)
    }

    private val viewModel: CurrencyExchangeRateViewModel by viewModel()

    private var from: String? = ""
    private var to: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countries = getAllCountries()
        setUpView(countries)
        initListeners()
    }

    private fun initListeners() {
        binding.convertButton.setOnClickListener {
            when {
                binding.amoutText.text!!.isEmpty() -> {
                    binding.amountLayout.apply {
                        error = "This field is required"
                        requestFocus()
                    }
                }
                else -> {
                    viewModel.getExchangeRates(
                        Constants.API_KEY,
                        from = from!!,
                        to = to!!,
                        binding.amoutText.text.toString().toDouble()
                    )
                    observeViewModel()
                }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.exchangeRates.collect { responseState ->
                when (responseState) {
                    is ResponseState.Loading -> {

                    }
                    is ResponseState.Result<*> -> {
                        val response = responseState.data as ConversionResponse
                        Timber.e("Response: $response")
                        setUpResultView(response)
                    }
                    is ResponseState.Error -> {
                        val error = responseState.message
                        Timber.e("Error: $error")
                    }
                }
            }
        }
    }

    private fun setUpResultView(response: ConversionResponse) {
        binding.apply {
            resultLayout.visibility = VISIBLE
            val rates = response.rates
            var amount:Double = 0.0
            var currencyName = ""
            rates.keys.forEach {
                currencyName = rates[it]?.currency_name.toString()
                amount = rates[it]?.rate_for_amount!!
            }
            currencyText.text = currencyName
            currencyResultText.text = amount.toString()

        }
    }

    private fun setUpView(countries: ArrayList<String>) {
        val fromAdapter = ArrayAdapter(binding.root.context, R.layout.list_item, countries)
        val toAdapter = ArrayAdapter(binding.root.context, R.layout.list_item, countries)
        binding.apply {

            fromText.setAdapter(fromAdapter)
            toText.setAdapter(toAdapter)

            fromText.setOnItemClickListener { _, _, _, _ ->
                val country = fromText.text.toString()
                val countryCode = getCountryCode(countryName = country)
                from = getCountryCurrency(countryCode = countryCode)
                Timber.e("To Currency: $from")
            }
            toText.setOnItemClickListener { _, _, _, _ ->
                val country = toText.text.toString()
                val countryCode = getCountryCode(countryName = country)
                to = getCountryCurrency(countryCode = countryCode)
                Timber.e("To Currency: $to")
            }
        }
    }

}