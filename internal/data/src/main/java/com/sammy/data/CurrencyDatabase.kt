package com.sammy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sammy.data.dao.RatesDao
import com.sammy.data.entity.RatesEntity

@Database(entities = [
    RatesEntity::class
],version = 1,exportSchema = false
)
abstract class CurrencyDatabase:RoomDatabase() {
    abstract fun ratesDao():RatesDao
}