package com.sammy.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.sammy.data.base.BaseDao
import com.sammy.data.entity.RatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RatesDao : BaseDao<RatesEntity> {

    @Query("SELECT * FROM rates")
    fun historicalRates(): Flow<List<RatesEntity>>
}