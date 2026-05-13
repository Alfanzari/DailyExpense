package com.syauqialfanzari0008.dailyexpense.data.local

import androidx.room.*
import com.syauqialfanzari0008.dailyexpense.data.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Query("SELECT * FROM expense WHERE isDeleted = 0 ORDER BY date DESC")
    fun getAllExpense(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE isDeleted = 1 ORDER BY date DESC")
    fun getDeletedExpense(): Flow<List<Expense>>

    @Query("UPDATE expense SET isDeleted = 1 WHERE id = :id")
    suspend fun softDelete(id: Int)

    @Query("UPDATE expense SET isDeleted = 0 WHERE id = :id")
    suspend fun restore(id: Int)

    @Delete
    suspend fun hardDelete(expense: Expense)
}