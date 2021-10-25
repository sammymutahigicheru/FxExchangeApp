package com.dvt.currencyexchangeapp.ui.histories.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dvt.core.Constants
import com.dvt.currencyexchangeapp.ui.histories.IHistoriesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sammy.data.entity.RatesEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.lang.reflect.Type

class UploadRatesWorker(
    appContext: Context, workerParams: WorkerParameters
) : Worker(appContext, workerParams), KoinComponent {
    val repository: IHistoriesRepository by inject()
    override fun doWork(): Result {
        try {
            // Get the input
            val inputData = inputData.getString(Constants.UPLOAD_KEY)

            val gson: Gson = Gson()
            val ratesListType: Type = object : TypeToken<List<RatesEntity>>() {}.type
            val data = gson.fromJson<List<RatesEntity>>(inputData, ratesListType)
            Timber.e("Worker RatesEntity: ${data}")
            upload(data)

            return Result.success()

        } catch (e: Exception) {
            Timber.e("Worker Failure: ${e.localizedMessage}")
            return Result.failure()
        }
    }

    fun upload(rates: List<RatesEntity>) {
        Timber.e("****Saving Rates******")
        repository.saveRates(rates)
    }


}