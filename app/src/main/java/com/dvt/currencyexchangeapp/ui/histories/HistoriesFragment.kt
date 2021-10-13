package com.dvt.currencyexchangeapp.ui.histories

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dvt.core.Constants
import com.dvt.core.extensions.convertToMonthDay
import com.dvt.core.helpers.convertTimeStamp
import com.dvt.core.helpers.isOnline
import com.dvt.core.mappers.toRatesEntity
import com.dvt.currencyexchangeapp.R
import com.dvt.currencyexchangeapp.databinding.FragmentHistoriesBinding
import com.dvt.currencyexchangeapp.model.Rates
import com.dvt.currencyexchangeapp.utils.ResponseState
import com.dvt.currencyexchangeapp.utils.mappers.toRate
import com.dvt.currencyexchangeapp.utils.mappers.toRates
import com.dvt.network.models.convert.ConversionResponse
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HistoriesFragment : Fragment() {
    private val binding: FragmentHistoriesBinding by lazy {
        FragmentHistoriesBinding.inflate(layoutInflater)
    }
    private val viewModel: HistoryViewModel by viewModel()

    private lateinit var progressDialog: MaterialAlertDialogBuilder

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog = MaterialAlertDialogBuilder(binding.root.context).apply {
            setCancelable(false)
        }

        val isNetworkAvailable = isOnline(binding.root.context)

        if (isNetworkAvailable){
            getHistoricalExchangeRatesForCurrentMonth()
            observeViewModel()
        }else{
            fetchHistoricalRatesFromDb()
        }
    }

    private fun fetchHistoricalRatesFromDb() {
        lifecycleScope.launchWhenStarted {
            viewModel.fetchHistoricalRates().collect { rates ->
                Timber.e("Historical Rates: $rates")
                setupChart(rates.toRate())
            }
        }
    }

    private fun getHistoricalExchangeRatesForCurrentMonth() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)

        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        val yearRange = (2017..currentYear).toList().reversed()
        binding.monthSpinner.onItemSelectedListener = null
        binding.monthSpinner.setSelection(currentMonth)

        val adapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_dropdown_item, yearRange)
        binding.yearSpinner.adapter = adapter

        addSpinnerListeners()

        val weeks = getNumberOfWeeks(currentYear, currentMonth)
        binding.weekGroup.removeAllViews()

        addChips(weeks)

        val firstDay = convertTimeStamp(calendar.time.time)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val lastDay = convertTimeStamp(calendar.time.time)

        Timber.e("first day $firstDay, last day $lastDay")

        getHistoricalExchangeRates(firstDay, lastDay)
    }

    private fun addChips(dateChunks: MutableList<List<String>>) {

        val dates = dateChunks[0].chunked(2)

        for (date in dates) {
            val dateRange = "${date.first().convertToMonthDay()} - ${date.last().convertToMonthDay()}"

            val chip = Chip(binding.root.context)

            with(chip) {
                text = dateRange
                chipEndPadding = 8F
                chipStartPadding = 8F
                isCheckable = true
                this.setOnCheckedChangeListener { _, isChecked ->

                    if (isChecked) {
                        Timber.e("chip dates $date")
                        getHistoricalExchangeRates(date.first(), date.last())

                    } else {
                        Timber.e("chip unchecked")
                    }
                }

            }.also { binding.weekGroup.addView(chip) }


        }

    }

    private fun getNumberOfWeeks(year: Int, month: Int): MutableList<List<String>> {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val weekdates: MutableList<List<String>> = ArrayList()
        val dates: MutableList<String> = ArrayList()
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, 1)
        while (c.get(Calendar.MONTH) == month) {
            while (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                c.add(Calendar.DAY_OF_MONTH, -1)
            }
            dates.add(format.format(c.time))

            c.add(Calendar.DAY_OF_MONTH, 6)
            dates.add(format.format(c.time))

            weekdates.add(dates)
            c.add(Calendar.DAY_OF_MONTH, 1)
        }

        Timber.e("date weeks $weekdates")

        return weekdates
    }

    private fun addSpinnerListeners() {
        with(binding) {
            monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    val currentYear = yearSpinner.selectedItem.toString().toInt()

                    val weeks = getNumberOfWeeks(currentYear, position)

                    binding.weekGroup.removeAllViews()

                    addChips(weeks)


                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

            yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val currentYear = yearSpinner.selectedItem.toString().toInt()
                    val currentMonth = monthSpinner.selectedItemPosition

                    val weeks = getNumberOfWeeks(currentYear, currentMonth)

                    binding.weekGroup.removeAllViews()

                    addChips(weeks)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
        }
    }

    private fun observeViewModel() {
        val progress = progressDialog.create()
        lifecycleScope.launchWhenStarted {
            viewModel.historicalExchangeRates.collect { responseState ->
                when (responseState) {

                    is ResponseState.Loading -> {
                        progress.apply {
                            setMessage("Getting historical exchange rates...")
                            show()
                        }
                    }
                    is ResponseState.Result<*> -> {
                        progress.hide()
                        val result = responseState.data as ConversionResponse
                        saveRates(result)
                        setupChart(result.toRates())
                    }
                    is ResponseState.Error -> {
                        progress.hide()
                        val message = responseState.message
                        Timber.e("Error: $message")
                    }
                }
            }
        }
    }

    private fun saveRates(result: ConversionResponse) {
        viewModel.saveRates(result.toRatesEntity())
    }

    private fun setupChart(result: List<Rates>) {

        binding.lineChart.apply {
            // background color
            setBackgroundColor(Color.WHITE)

            // disable description text
            description.isEnabled = false

            setDrawGridBackground(false)


            // enable scaling and dragging
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)

            setLimits()

        }
        binding.apply {
            seekBar1.max = 170
            seekBar2.max = 220
            seekBar1.progress = 170
            seekBar2.progress = 220
            setData(result)

            // draw points over time

            // draw points over time
            lineChart.animateX(1500)

            // get the legend (only possible after setting data)

            // get the legend (only possible after setting data)
            val l: Legend = lineChart.legend

            // draw legend entries as lines

            // draw legend entries as lines
            l.form = LegendForm.LINE
        }
    }

    private fun setLimits() {
        binding.lineChart.apply {
            //x-axis
            val xAxis = xAxis

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f)


            //y-axis
            val yAxis = axisLeft;

            // disable dual axis (only use LEFT axis)
            axisRight.isEnabled = false;

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.axisMaximum = 200f;
            yAxis.axisMinimum = -50f;

            //limit lines
            val llXAxis = LimitLine(9f, "Index 10")
            llXAxis.lineWidth = 4f
            llXAxis.enableDashedLine(10f, 10f, 0f)
            llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
            llXAxis.textSize = 10f
            //  llXAxis.typeface = tfRegular

            val ll1 = LimitLine(150f, "Upper Limit")
            ll1.lineWidth = 4f
            ll1.enableDashedLine(10f, 10f, 0f)
            ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
            ll1.textSize = 10f
            //  ll1.typeface = tfRegular

            val ll2 = LimitLine(-30f, "Lower Limit")
            ll2.lineWidth = 4f
            ll2.enableDashedLine(10f, 10f, 0f)
            ll2.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
            ll2.textSize = 10f
            // ll2.typeface = tfRegular

            // draw limit lines behind data instead of on top

            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true)
            xAxis.setDrawLimitLinesBehindData(true)

            // add limit lines

            // add limit lines
            yAxis.addLimitLine(ll1)
            yAxis.addLimitLine(ll2)
        }
    }

    private fun setData(result: List<Rates>) {
        Timber.e("Converted Rates: $result")
        val set1: LineDataSet
        val values: ArrayList<Entry> = ArrayList()
        for (item in result) {
            values.add(Entry(item.id.toFloat(), item.rates.toFloat()))
        }

        if (binding.lineChart.data != null &&
                binding.lineChart.data.getDataSetCount() > 0) {
            set1 = binding.lineChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            binding.lineChart.data.notifyDataChanged()
            binding.lineChart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")
            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> binding.lineChart.axisLeft.axisMinimum }

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable: Drawable = ContextCompat.getDrawable(binding.root.context, R.drawable.fade_red)!!
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }
            val dataSets: ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            binding.lineChart.data = data
        }
    }

    private fun getHistoricalExchangeRates(firstDay: String, lastDay: String) {
        viewModel.getHistoricalExchangeRates(firstDay, Constants.API_KEY)
    }
}