package com.syauqialfanzari0008.dailyexpense.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.syauqialfanzari0008.dailyexpense.data.local.ExpenseDatabase
import com.syauqialfanzari0008.dailyexpense.data.model.Expense
import com.syauqialfanzari0008.dailyexpense.utils.PreferenceManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = ExpenseDatabase.getDatabase(application).expenseDao()

    val expenses = dao.getAllExpense().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val deletedExpenses = dao.getDeletedExpense().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val isGridView = PreferenceManager.isGridView(application).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    fun addExpense(expense: Expense) = viewModelScope.launch {
        dao.insert(expense)
    }

    fun updateExpense(expense: Expense) = viewModelScope.launch {
        dao.update(expense)
    }

    fun deleteExpense(id: Int) = viewModelScope.launch {
        dao.softDelete(id)
    }

    fun restoreExpense(id: Int) = viewModelScope.launch {
        dao.restore(id)
    }

    fun hardDeleteExpense(expense: Expense) = viewModelScope.launch {
        dao.hardDelete(expense)
    }

    fun setGridView(isGrid: Boolean) = viewModelScope.launch {
        PreferenceManager.setGridView(getApplication(), isGrid)
    }
}