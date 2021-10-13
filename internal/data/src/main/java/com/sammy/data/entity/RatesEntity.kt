package com.sammy.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class RatesEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val rates:String
)