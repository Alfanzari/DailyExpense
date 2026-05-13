package com.syauqialfanzari0008.dailyexpense.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: String,
    val note: String = "",
    val date: Long = System.currentTimeMillis(),
    val isDeleted: Boolean = false
)